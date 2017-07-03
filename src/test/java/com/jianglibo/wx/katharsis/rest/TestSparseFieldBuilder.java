package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.jianglibo.wx.util.MyJsonApiUrlBuilder;

public class TestSparseFieldBuilder {

	@Test
	public void t() {
		MyJsonApiUrlBuilder sfb = new MyJsonApiUrlBuilder("?");
		String s = sfb.resouceFields("users", "id", "email").resouceFields("posts", "id").build();
		assertThat(s, equalTo("?fields[users]=id,email&fields[posts]=id"));
		
		sfb = new MyJsonApiUrlBuilder("?");
		s = sfb.resouceFields("users", "id", "email").resouceFields("posts", "id").includes("users", "abc").build();
		assertThat(s, equalTo("?include=users,abc&fields[users]=id,email&fields[posts]=id"));
		
		s = new MyJsonApiUrlBuilder("?").includes("requester","receiver").resouceFields("users", "id").build();
		System.out.print(s);

	}
}
