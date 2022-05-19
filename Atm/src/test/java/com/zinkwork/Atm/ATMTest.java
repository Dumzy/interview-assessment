package com.zinkwork.Atm;

import com.zinkwork.Atm.controller.AtmAdminControllerTest;
import com.zinkwork.Atm.controller.AtmControllerTest;
import com.zinkwork.Atm.service.AtmAdminServiceTest;
import com.zinkwork.Atm.util.AccountBalanceValidationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ AtmAdminControllerTest.class, AtmControllerTest.class, AtmAdminControllerTest.class,
        AtmAdminServiceTest.class, AtmAdminServiceTest.class, AccountBalanceValidationTest.class })
class ATMTest {

    @BeforeAll
    public static void startup() {
        System.out.println("UNIT TEST STARTING UP");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("UNIT TEST COMPLETION");
    }
}
