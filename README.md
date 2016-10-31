# AndroidFastDevLib
Android快速开发框架

	一.基本内容
		1.网络请求封装包括以下内容(RxJava+Retrofit)
			返回数据统一处理，retrofit (okhttp)处理，rx封装处理
			缓存处理, 策略模式...过期策略
			log处理
			性能监控
			loading处理
			流程封装模板
			发送请求
			处理一些具体的事 代理缓存策略
			回调处理
		
		2.loading
		
		3.常用自定义View
		
		4.常用工具类(APP自动更新)
		
	二.使用
		1.网络模块(更多看Simple)
			Api baseApi = ApiServicesFactory.getInstance().produceApi(Api.class);
			RxSubscriber<List<TestEntity>> rxSubscriber = new RxSubscriber<List<TestEntity>>() {
				@Override
				public void onNext(List<TestEntity> listTestResponseEntity) {
					super.onNext(listTestResponseEntity);
					tv.setText(listTestResponseEntity.toString());
				}
			};
			Subscription subscription = RxHelper.dealTest(baseApi.getTest(), rxSubscriber);
		 
	三.原理
		1.网络封装
			1).okhttp 配置
				l.拦截器配置
				
				2.https设置
				
			2).异常处理
			3).返回数据统一处理
			
			4).文件上传包装
		
			5)Rx封装
			6)缓存封装
			
			
			
