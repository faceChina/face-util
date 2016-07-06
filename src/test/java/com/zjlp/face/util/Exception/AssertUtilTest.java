package com.zjlp.face.util.Exception;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.exception.BaseException;

public class AssertUtilTest {
	
	//@Test
	public void testNotTrue() {
		try {
			AssertUtil.notTrue(true, CErrMsg.NULL_PARAM, "hoihik");
			Assert.fail();
		} catch (BaseException e) {
			Assert.assertEquals(e.getMessage(), CErrMsg.NULL_PARAM.getErrCd());
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	//@Test
	public void testNotTrue1() {
		try {
			AssertUtil.notTrue(false, CErrMsg.NULL_PARAM, "hoihik");
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	//@Test
	public void testisTrue() {
		try {
			AssertUtil.isTrue(true, CErrMsg.NULL_PARAM, "AAAA");
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	//@Test
	public void testIsTrue1(){
		try {
			AssertUtil.isTrue(false, CErrMsg.NULL_PARAM, "AAAA");
			Assert.fail();
		} catch (BaseException e) {
			Assert.assertEquals(e.getMessage(), CErrMsg.NULL_PARAM.getErrCd());
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
//	@Test
	public void testNotEmpty(){
		try {
			AssertUtil.notEmpty(CollectionUtils.EMPTY_COLLECTION, CErrMsg.NULL_PARAM, "AAAA");
			Assert.fail();
		} catch (BaseException e) {
			Assert.assertEquals(e.getMessage(), CErrMsg.NULL_PARAM.getErrCd());
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testIsEmpty(){
		try {
			AssertUtil.isEmpty(CollectionUtils.EMPTY_COLLECTION,  CErrMsg.NULL_PARAM, "AAAA");
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	//@Test
	public void testnotNull(){
		try {
			AssertUtil.notNull(null, CErrMsg.NULL_PARAM, "BBBB");
			Assert.fail();
		} catch (BaseException e) {
			Assert.assertEquals(e.getMessage(), CErrMsg.NULL_PARAM.getErrCd());
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	
	//@Test
	public void testisNull(){
		try {
			AssertUtil.isNull(null, CErrMsg.NULL_PARAM, "BBBB");
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	//@Test
	public void testhasLength(){
		try {
			AssertUtil.hasLength("", CErrMsg.NULL_PARAM, "CCCC");
			Assert.fail();
		} catch (BaseException e) {
			Assert.assertEquals(e.getMessage(), CErrMsg.NULL_PARAM.getErrCd());
		} catch (Exception e) {
			Assert.fail();
		}
	}
	//@Test
	public void testhasLength1(){
		try {
			AssertUtil.hasLength("111", CErrMsg.NULL_PARAM, "CCCC");
		} catch (Exception e) {
			Assert.fail();
		}
	}

}
