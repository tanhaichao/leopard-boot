#项目结构
example #根目录
	doc	#项目文档
	
example-model #枚举
	com.company.example.onum #枚举包
		
example-service #Service、Dao模块
	com.company.example.serializer	#Json序列化器
	com.company.example.xxx		#按实体类做包名划分
	com.company.example.util	#util包
		com.company.example.util.CheckUtil	#参数合法性检查(抛异常)
		com.company.example.util.ValidUtil	#参数合法性验证(返回boolean类型)
	
example-web	#Controller模块
	com.company.example.web.JettyTest	#Jetty测试类

	