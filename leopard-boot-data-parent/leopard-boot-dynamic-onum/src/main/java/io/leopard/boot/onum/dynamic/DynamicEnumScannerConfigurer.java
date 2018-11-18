// package io.leopard.boot.onum.dynamic;
//
// import org.apache.commons.lang3.StringUtils;
// import org.apache.commons.logging.Log;
// import org.apache.commons.logging.LogFactory;
// import org.springframework.beans.BeansException;
// import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
// import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
// import org.springframework.beans.factory.support.BeanDefinitionRegistry;
// import org.springframework.context.ApplicationContext;
// import org.springframework.context.ApplicationContextAware;
//
// import io.leopard.boot.onum.dynamic.model.DynamicEnumInfo;
// import io.leopard.boot.onum.dynamic.service.DynamicEnumManager;
// import io.leopard.lang.inum.Bnum;
// import io.leopard.lang.inum.Inum;
// import io.leopard.lang.inum.Snum;
// import io.leopard.lang.inum.dynamic.AbstractBnum;
// import io.leopard.lang.inum.dynamic.AbstractInum;
// import io.leopard.lang.inum.dynamic.AbstractSnum;
//
//// @Component
// TODD 编程范式
// public class DynamicEnumScannerConfigurer implements BeanFactoryPostProcessor, ApplicationContextAware {
//
// protected Log logger = LogFactory.getLog(this.getClass());
//
// private ApplicationContext applicationContext;
//
// @Override
// public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
// this.applicationContext = applicationContext;
// // new Exception("setApplicationContext").printStackTrace();
//
// }
//
// // @PostConstruct
// // public void init() {
// // new Exception("init").printStackTrace();
// // dynamicEnumService.setEnumList(enumList);
// // dynamicEnumService.update();
// // }
//
// @Override
// public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
// // new Exception("postProcessBeanFactory").printStackTrace();
//
// // logger.info("postProcessBeanFactory");
// String basePackage = "cn.nineox.zhaotong.scp";// FIXME configurer.getProperty("base.package");
// // logger.info("basePackage:" + basePackage);
// if (StringUtils.isEmpty(basePackage)) {
// throw new RuntimeException("app.properties没有配置base.package属性.");
// }
// // System.err.println("DynamicEnumScannerConfigurer postProcessBeanFactory");
//
// DynamicEnumScanner scanner = new DynamicEnumScanner((BeanDefinitionRegistry) beanFactory) {
// @Override
// protected void registerEnum(String enumId, String beanClassName) {
// Class<?> enumType = this.parseEnumType(beanClassName);
//
// DynamicEnumInfo enumInfo = new DynamicEnumInfo();
// enumInfo.setEnumId(enumId);
// enumInfo.setBeanClassName(beanClassName);
// enumInfo.setEnumType(enumType);
// // enumList.add(enumInfo);
// DynamicEnumManager.addDynamicEnumInfo(enumInfo);
//
// DynamicEnumManager.addBean(enumId, beanClassName);
// }
//
// /**
// * 根据className解析枚举类型.
// *
// * @param beanClassName
// * @return
// */
// @Override
// protected Class<?> parseEnumType(String beanClassName) {
// Class<?> clazz;
// try {
// clazz = Class.forName(beanClassName);
// }
// catch (ClassNotFoundException e) {
// throw new RuntimeException(e.getMessage(), e);
// }
//
// if (AbstractSnum.class.isAssignableFrom(clazz)) {
// return Snum.class;
// }
// else if (AbstractInum.class.isAssignableFrom(clazz)) {
// return Inum.class;
// }
// else if (AbstractBnum.class.isAssignableFrom(clazz)) {
// return Bnum.class;
// }
// else {
// throw new IllegalArgumentException("未知动态枚举类型[" + clazz.getName() + "].");
// }
// }
//
// };
// scanner.setResourceLoader(this.applicationContext);
// scanner.scan(basePackage);
//
// // DynamicEnumService dynamicEnumService = beanFactory.getBean(DynamicEnumService.class);
// // dynamicEnumService.setEnumList(enumList);
// // dynamicEnumService.update();
// }
//
// }
