package com.book;

import com.book.factory.BookFactoryContextTest;
import com.book.factory.EducationalBookFactoryTest;
import com.book.factory.FictionBookFactoryTest;
import com.book.integration.FactoryAndStrategyIntegrationTest;
import com.book.service.context.BookSortingContextTest;
import com.book.strategy.TitleSortingStrategyTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 设计模式测试套件
 * 包含工厂模式和策略模式的所有单元测试
 *
 * @author Test Suite
 * @date 2025/01/27
 */
@Suite
@SuiteDisplayName("设计模式测试套件")
@SelectClasses({
        // 工厂模式测试
        BookFactoryContextTest.class,
        EducationalBookFactoryTest.class,
        FictionBookFactoryTest.class,
        
        // 策略模式测试
        BookSortingContextTest.class,
        TitleSortingStrategyTest.class,
        
        // 集成测试
        FactoryAndStrategyIntegrationTest.class
})
public class AllPatternTests {
    // 测试套件，无需额外代码
} 