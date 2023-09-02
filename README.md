

原本是论坛项目中实时公共交流版块的组件

后抽取出来开发成了一套单独的工具，尚不算太完善，没有确认机制（才发现早就有netty socketIO这种东西了）


注入都是些spring的基本用法，没多高深，用例可在top.ashonecoder.ashonewebsocket.processor.impl.UserProcessor
中看到

使用时需要用@import注解导入WebSocketConfig.class，并且实现top.ashonecoder.ashonewebsocket.identity.AuthenticationIdentity