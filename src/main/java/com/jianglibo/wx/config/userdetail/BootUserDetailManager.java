package com.jianglibo.wx.config.userdetail;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Role;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.RoleFacadeRepository;
import com.jianglibo.wx.util.PairForStream;
import com.jianglibo.wx.util.SecurityUtil;
import com.jianglibo.wx.vo.BootUserPrincipal;
import com.jianglibo.wx.vo.RoleNames;


@Component
public class BootUserDetailManager implements UserDetailsManager {
	
	private static final String ROLE_PREFIX = "ROLE_";
	
	private static final String ROLE_USER_NAME = "ROLE_USER";
    
    private static Pattern mobilePtn = Pattern.compile("^[0-9+-]+$");
    
    private static Logger logger = LoggerFactory.getLogger(BootUserDetailManager.class);
    
    private BootUserFacadeRepository userRepo;
    
    private RoleFacadeRepository roleRepo;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public BootUserDetailManager(BootUserFacadeRepository userRepo, RoleFacadeRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public BootUserPrincipal loadUserByUsername(String emailOrMobile) throws UsernameNotFoundException {
        BootUser person;
        
        if (emailOrMobile.indexOf('@') != -1) {
            person = userRepo.findByEmail(emailOrMobile);
        } else if(mobilePtn.matcher(emailOrMobile).find()) {
            person = userRepo.findByMobile(emailOrMobile);
        } else {
            person = userRepo.findByName(emailOrMobile); 
        }
        if (person == null) {
            throw new UsernameNotFoundException(emailOrMobile);
        }
        return new BootUserPrincipal(person);
    }
    
    public BootUser createUserAndReturn(UserDetails bootUserVo) {
        BootUserPrincipal bootUserVoLocal = (BootUserPrincipal) bootUserVo;

        // maybe is for the vary first run.
        final boolean initRun = SecurityUtil.isAuthenticationNull() && roleRepo.count() == 0;
        
        // @formatter:off
         Set<Role> roleset = bootUserVoLocal.getAuthorities().stream()
      		   .map(ga -> ga.getAuthority())
      		   .map(gn -> gn.startsWith(ROLE_PREFIX) ? gn : ROLE_PREFIX + gn)
      		   .map(String::toUpperCase)
      		   .map(rn -> new PairForStream<String, Role>(rn, roleRepo.findByName(rn)))
      		   .map(ot -> {
      			   if (ot.getSecond() == null ) {
      				   logger.info("start insert role {}", ot.getFirst());
      				   Role r = new Role(ot.getFirst());
      				   if (initRun) {
      					 return roleRepo.initSave(r);
      				   } else {
      					 return roleRepo.save(r);
      				   }
      			   } else {
      				   return ot.getSecond();
      			   }
      		   }).collect(Collectors.toSet());
         // @formatter:on
         
        Role dr = roleRepo.findByName(RoleNames.USER);
    	if (dr == null) {
    		if (initRun) {
    			dr = roleRepo.initSave(new Role(RoleNames.USER));
    		} else {
    			dr = roleRepo.save(new Role(RoleNames.USER));
    		}
    	}

  	   if (!roleset.stream().anyMatch(r -> ROLE_USER_NAME.equals(r.getName()))) {
  		   roleset.add(dr);
  	   }
	     
	     BootUser bootUser = userRepo.findByName(bootUserVo.getUsername());
	     if (bootUser == null) {
	  	   bootUser = new BootUser(bootUserVoLocal, passwordEncoder.encode(bootUserVoLocal.getPassword()));
	     }
	     bootUser.setRoles(roleset);
	     return userRepo.save(bootUser);
    }

    @Override
    public void createUser(UserDetails bootUserVo) {
    	createUserAndReturn(bootUserVo);
    }

    @Override
    public void updateUser(UserDetails user) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteUser(String username) {
    	BootUser bootUser = userRepo.findByName(username);
        userRepo.delete(bootUser.getId());
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
		Authentication currentUser = SecurityContextHolder.getContext()
				.getAuthentication();

		if (currentUser == null) {
			// This would indicate bad coding somewhere
			throw new AccessDeniedException(
					"Can't change password as no Authentication object found in context "
							+ "for current user.");
		}

		String username = currentUser.getName();
		// If an authentication manager has been set, re-authenticate the user with the
		// supplied password.
		if (authenticationManager != null) {
			logger.debug("Reauthenticating user '" + username
					+ "' for password change request.");
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					username, oldPassword));
		}
		else {
			logger.debug("No authentication manager set. Password won't be re-checked.");
		}

		logger.debug("Changing password for user '" + username + "'");
		BootUserPrincipal pvo = (BootUserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		BootUser p = userRepo.findOne(pvo.getId(), true);		
		p.setPassword(passwordEncoder.encode(newPassword));		
		userRepo.save(p);
		SecurityContextHolder.getContext().setAuthentication(
				createNewAuthentication(currentUser, newPassword));
//		userCache.removeUserFromCache(username);
    }
    
	protected Authentication createNewAuthentication(Authentication currentAuth,
			String newPassword) {
		UserDetails user = loadUserByUsername(currentAuth.getName());
		UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
				user, null, user.getAuthorities());
		newAuthentication.setDetails(currentAuth.getDetails());
		return newAuthentication;
	}

    public boolean userExists(String emailOrUsernameOrMobile,String tag) {
    	BootUser person;
    	switch(tag){
    	case "username":
            person = userRepo.findByName(emailOrUsernameOrMobile);
            break;
    	case "email":
            person = userRepo.findByEmail(emailOrUsernameOrMobile);
            break;
    	case "mobile":
            person = userRepo.findByMobile(emailOrUsernameOrMobile);
            break; 
        default:
            	person = null;
    	}
    	if(person == null){
            return false;
    	}else{
    		return true;
    	}
    }
    
    public BootUser findByName(String username){
    	return userRepo.findByName(username);
    }
    
	@Override
	public boolean userExists(String username) {
		// TODO Auto-generated method stub
		return false;
	}
    
    //~ UserDetailsManager implementation ==============================================================================

//    public void createUser(final UserDetails user) {
//        validateUserDetails(user);
//        getJdbcTemplate().update(createUserSql, new PreparedStatementSetter() {
//            public void setValues(PreparedStatement ps) throws SQLException {
//                ps.setString(1, user.getUsername());
//                ps.setString(2, user.getPassword());
//                ps.setBoolean(3, user.isEnabled());
//            }
//
//        });
//
//        if (getEnableAuthorities()) {
//            insertUserAuthorities(user);
//        }
//    }
//
//    public void updateUser(final UserDetails user) {
//        validateUserDetails(user);
//        getJdbcTemplate().update(updateUserSql, new PreparedStatementSetter() {
//            public void setValues(PreparedStatement ps) throws SQLException {
//                ps.setString(1, user.getPassword());
//                ps.setBoolean(2, user.isEnabled());
//                ps.setString(3, user.getUsername());
//            }
//        });
//
//        if (getEnableAuthorities()) {
//            deleteUserAuthorities(user.getUsername());
//            insertUserAuthorities(user);
//        }
//
//        userCache.removeUserFromCache(user.getUsername());
//    }
//
//    private void insertUserAuthorities(UserDetails user) {
//        for (GrantedAuthority auth : user.getAuthorities()) {
//            getJdbcTemplate().update(createAuthoritySql, user.getUsername(), auth.getAuthority());
//        }
//    }
//
//    public void deleteUser(String username) {
//        if (getEnableAuthorities()) {
//            deleteUserAuthorities(username);
//        }
//        getJdbcTemplate().update(deleteUserSql, username);
//        userCache.removeUserFromCache(username);
//    }
//
//    private void deleteUserAuthorities(String username) {
//        getJdbcTemplate().update(deleteUserAuthoritiesSql, username);
//    }
//
//    public void changePassword(String oldPassword, String newPassword) throws AuthenticationException {
//        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
//
//        if (currentUser == null) {
//            // This would indicate bad coding somewhere
//            throw new AccessDeniedException("Can't change password as no Authentication object found in context " +
//                    "for current user.");
//        }
//
//        String username = currentUser.getName();
//
//        // If an authentication manager has been set, re-authenticate the user with the supplied password.
//        if (authenticationManager != null) {
//            logger.debug("Reauthenticating user '"+ username + "' for password change request.");
//
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
//        } else {
//            logger.debug("No authentication manager set. Password won't be re-checked.");
//        }
//
//        logger.debug("Changing password for user '"+ username + "'");
//
//        getJdbcTemplate().update(changePasswordSql, newPassword, username);
//
//        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(currentUser, newPassword));
//
//        userCache.removeUserFromCache(username);
//    }
//
//    protected Authentication createNewAuthentication(Authentication currentAuth, String newPassword) {
//        UserDetails user = loadUserByUsername(currentAuth.getName());
//
//        UsernamePasswordAuthenticationToken newAuthentication =
//                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//        newAuthentication.setDetails(currentAuth.getDetails());
//
//        return newAuthentication;
//    }
//
//    public boolean userExists(String username) {
//        List<String> users = getJdbcTemplate().queryForList(userExistsSql, new String[] {username}, String.class);
//
//        if (users.size() > 1) {
//            throw new IncorrectResultSizeDataAccessException("More than one user found with name '" + username + "'", 1);
//        }
//
//        return users.size() == 1;
//    }
}
