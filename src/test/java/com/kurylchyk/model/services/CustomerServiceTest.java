package com.kurylchyk.model.services;


import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages("com.kurylchyk.model.services.impl.customerCommand")
public class CustomerServiceTest {
}
