package com.jianglibo.wx.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

public class TestBeanUtils {
	
	@Test
	public void t() {
		Ac source = new Ac(1, "s", "s1", "s2");
		AcDto target = new AcDto();
		BeanUtils.copyProperties(source, target);
		assertThat(target.getI(), equalTo(1));
		assertThat(target.getS(), equalTo("s"));
		assertThat(target.getL().get(0), equalTo("s1"));
		assertThat(target.getL().get(1), equalTo("s2"));
		
		source = new Ac(1, "s", "s1", "s2");
		target = new AcDto();
		BeanUtils.copyProperties(source, target, "l");
		assertThat(target.getL().size(), equalTo(0));
	}
	
	@Test(expected=ClassCastException.class)
	public void t1() {
		Ac source = new Ac(1, "s", "s1", "s2");
		AcDto1 target = new AcDto1();
		BeanUtils.copyProperties(source, target);
		assertThat(target.getI(), equalTo(1));
		assertThat(target.getS(), equalTo("s"));
		assertThat(target.getL().get(0), equalTo("s1"));
		assertThat(target.getL().get(1), equalTo("s2"));
	}
	
	protected static class Ac {
		private int i;
		private String s;
		private List<String> l = new ArrayList<>();
		
		public Ac(int i, String s, String...ss) {
			this.i = i;
			this.s = s;
			for(String as : ss) {
				l.add(as);
			}
		}
		public int getI() {
			return i;
		}
		public void setI(int i) {
			this.i = i;
		}
		public String getS() {
			return s;
		}
		public void setS(String s) {
			this.s = s;
		}
		public List<String> getL() {
			return l;
		}
		public void setL(List<String> l) {
			this.l = l;
		}
	}
	
	protected static class AcDto1 {
		private int i;
		private String s;
		private List<Integer> l = new ArrayList<>();
		
		
		public int getI() {
			return i;
		}
		public void setI(int i) {
			this.i = i;
		}
		public String getS() {
			return s;
		}
		public void setS(String s) {
			this.s = s;
		}
		public List<Integer> getL() {
			return l;
		}
		public void setL(List<Integer> l) {
			this.l = l;
		}

	}
	
	protected static class AcDto {
		private int i;
		private String s;
		private List<String> l = new ArrayList<>();
		
		
		public int getI() {
			return i;
		}
		public void setI(int i) {
			this.i = i;
		}
		public String getS() {
			return s;
		}
		public void setS(String s) {
			this.s = s;
		}
		public List<String> getL() {
			return l;
		}
		public void setL(List<String> l) {
			this.l = l;
		}
	}


}
