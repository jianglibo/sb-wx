## modifying wafer-session-server to force returning of openId.
* Csessioninfo_Service.php -> check_session_for_auth,  return whole $result instead of $result['user_info']
* In Auth.php -> auth($id, $skey) -> add openid to userInfo.

$arr_result['user_info']->openId = $result->openId

## new resource creating steps.
* new entity domain.
* new dto
* new facaderepository.
* new jpa facade implementation.
* new repostory
* new convertor
* new dto repository