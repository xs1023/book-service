# 设计模式单元测试说明

## 概述

本项目包含工厂模式和策略模式的完整单元测试，确保设计模式的正确实现和功能验证。

## 测试结构

### 工厂模式测试

#### 1. BookFactoryContextTest
- **位置**: `src/test/java/com/book/factory/BookFactoryContextTest.java`
- **功能**: 测试工厂上下文类的核心功能
- **测试场景**:
  - 创建教育类书籍
  - 创建小说类书籍
  - 处理不支持的书籍类型
  - 处理空值和异常情况

#### 2. EducationalBookFactoryTest
- **位置**: `src/test/java/com/book/factory/EducationalBookFactoryTest.java`
- **功能**: 测试教育类书籍工厂
- **测试场景**:
  - 正常创建教育类书籍
  - 处理最小数据
  - 处理空数据
  - 异常情况处理

#### 3. FictionBookFactoryTest
- **位置**: `src/test/java/com/book/factory/FictionBookFactoryTest.java`
- **功能**: 测试小说类书籍工厂
- **测试场景**:
  - 正常创建小说类书籍
  - 处理最小数据
  - 处理空数据
  - 异常情况处理

### 策略模式测试

#### 1. BookSortingContextTest
- **位置**: `src/test/java/com/book/service/context/BookSortingContextTest.java`
- **功能**: 测试排序上下文类的核心功能
- **测试场景**:
  - 使用标题排序策略
  - 使用模拟排序策略
  - 处理未知排序策略
  - 处理空列表和异常情况

#### 2. TitleSortingStrategyTest
- **位置**: `src/test/java/com/book/strategy/TitleSortingStrategyTest.java`
- **功能**: 测试标题排序策略
- **测试场景**:
  - 多本书籍排序
  - 空列表处理
  - 单本书籍处理
  - 包含null标题的处理
  - 重复标题处理
  - 中英文混合排序

### 集成测试

#### FactoryAndStrategyIntegrationTest
- **位置**: `src/test/java/com/book/integration/FactoryAndStrategyIntegrationTest.java`
- **功能**: 测试工厂模式和策略模式的组合使用
- **测试场景**:
  - 创建并排序书籍
  - 混合类型书籍处理
  - 错误处理
  - 性能测试

## 运行测试

### 运行所有测试
```bash
mvn test
```

### 运行特定测试类
```bash
mvn test -Dtest=BookFactoryContextTest
mvn test -Dtest=TitleSortingStrategyTest
```

### 运行测试套件
```bash
mvn test -Dtest=AllPatternTests
```

### 运行集成测试
```bash
mvn test -Dtest=FactoryAndStrategyIntegrationTest
```

## 测试覆盖率

测试覆盖了以下方面：

1. **正常流程测试**: 验证设计模式在正常情况下的工作
2. **边界条件测试**: 测试空值、空列表等边界情况
3. **异常处理测试**: 验证错误情况的正确处理
4. **性能测试**: 确保在大数据集下的性能表现
5. **集成测试**: 验证多个设计模式的协同工作

## 测试数据

测试使用了真实的书籍数据，包括：
- 《三体》- 科幻小说
- 《Java编程思想》- 教育类书籍
- 《算法导论》- 教育类书籍
- 其他测试用书籍

## 注意事项

1. 测试使用H2内存数据库，无需外部数据库配置
2. 所有测试都是独立的，不依赖外部服务
3. 测试包含了详细的断言验证，确保功能正确性
4. 性能测试设置了合理的时间限制（1秒内完成）

## 扩展建议

如需添加新的测试场景，可以：

1. 在现有测试类中添加新的测试方法
2. 创建新的策略实现并添加相应测试
3. 添加新的工厂实现并添加相应测试
4. 扩展集成测试以覆盖更多组合场景 