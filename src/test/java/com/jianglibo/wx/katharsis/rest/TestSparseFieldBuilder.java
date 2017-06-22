package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.jianglibo.wx.KatharsisBase.SparseFieldBuilder;

public class TestSparseFieldBuilder {

	@Test
	public void t() {
		SparseFieldBuilder sfb = new SparseFieldBuilder("?");
		String s = sfb.resouceFields("users", "id", "email").resouceFields("posts", "id").build();
		assertThat(s, equalTo("?fields[users]=id,email&fields[posts]=id"));
		
		
		sfb = new SparseFieldBuilder("?");
		s = sfb.resouceFields("users", "id", "email").resouceFields("posts", "id").includes("users", "abc").build();
		assertThat(s, equalTo("?include=users,abc&fields[users]=id,email&fields[posts]=id"));
		
		s = new SparseFieldBuilder("?").includes("requester","receiver").resouceFields("users", "id").build();
		System.out.print(s);

	}
}
