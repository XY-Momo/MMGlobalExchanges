MMGlobalExchanges
==
我的世界/Minecraft丨1.12.2丨spigot丨插件服务端丨全球交易/全球市场丨黑市

前置
==
需要vault作为经济系统、PlaceholderAPI及Vault的扩展、DragonCore(如果需要搜索界面、文件会附一个简单的例子)

介绍
==
这是一个用于我的世界1.12.2插件服务器的玩家交易市场插件  
其中界面包括:  

    (黑市)商品总览  
    (黑市)个人信息界面  
    (黑市)个人出售记录界面  
    (黑市)个人购买记录界面  
    (黑市)个人上架管理界面  
    (黑市)出售收取界面  
    (黑市)筛选输入界面(需要龙核界面配合可能之后会更新原版搜索界面吧)  

目前仅用于vault金币的交易

联系
==
QQ:1756784800

演示
==
市场(黑市)主界面
--
上方显示商品  
最下方则是功能区，从左到右分别是上架、搜索、第一页、上一页、个人信息、下一页、最后一页、信息提示、关闭  

![image](https://github.com/user-attachments/assets/dec7483a-23c5-4a97-b9a9-b94c154fac73)  

市场(黑市)选择上架商品界面
--
屏障是返回主界面、纸是信息提示  
选择背包中的物品进行上架  

![image](https://github.com/user-attachments/assets/06558ae0-a8ec-455f-adb4-7e84c2c6e12d)


市场(黑市)上架界面
--
商品上架界面  
第一格是选中的物品点击后则会返回选择上架商品界面，后面的格子是价格最后位增加0、价格最后位增加1、价格最后位增加2、价格最后位增加3、价格最后位增加4、价格最后位增加5、价格最后位增加6、价格最后位增加7、价格最后位增加8、价格最后位增加9、清除价格、确定、价格退格、信息  
最后一个屏障是返回

![image](https://github.com/user-attachments/assets/34535112-214b-4d6d-b21e-027e51985de7)

市场(黑市)搜索界面
--
此界面配合龙核界面使用(文件中有一份简单的默认配置)，演示如下图  
不输入字符点确定则是不检索(由于没有做本地化物品名对应Material字典，所以原版物品名无法直接搜名字 比如草方块->GRASS)  

![image](https://github.com/user-attachments/assets/2bcd31ae-291f-4cc7-9729-4e27aaa69a82)
![image](https://github.com/user-attachments/assets/9d43853e-c44c-48fe-a11d-28e92c3a1ec1)
![image](https://github.com/user-attachments/assets/b02e3b8a-d7c7-4a3c-ab91-734610a01dfa)
![image](https://github.com/user-attachments/assets/ed13897b-8a52-4a81-8790-14a3822365d3)

市场(黑市)个人信息界面
--
上面从左到右分别是购买记录、出售记录、购买信息、出售信息、上架管理、出售收取  

![image](https://github.com/user-attachments/assets/d742609c-c87c-4197-877f-947dd4108f8a)

市场(黑市)上架管理界面
--
上面显示正在上架的物品点击后可以取消上架
左下角也可以上架，屏障是返回，箭是翻页、纸是信息  

![image](https://github.com/user-attachments/assets/80b3b1bb-fea7-4256-bd6d-e21858a0910f)
