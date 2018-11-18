#项目结构
example #根目录
	doc	#项目文档
	
example-model #枚举
	io.leopard.archetype.example.onum #枚举包
		
example-service #Service、Dao模块
	io.leopard.archetype.example.serializer	#Json序列化器
	io.leopard.archetype.example.xxx		#按实体类做包名划分
	io.leopard.archetype.example.util	#util包
		io.leopard.archetype.example.util.CheckUtil	#参数合法性检查(抛异常)
		io.leopard.archetype.example.util.ValidUtil	#参数合法性验证(返回boolean类型)
	
example-web	#Controller模块
	io.leopard.archetype.example.web.JettyTest	#Jetty测试类

#表结构
	doc/sql/example.sql
	
	