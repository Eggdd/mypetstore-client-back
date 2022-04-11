# mypetstore-client-back

#### 介绍
客户端宠物商店的后端

#### 一、商品展示模块

1.  获得所有category类信息

   method: get

   url: /catalog/categories

   parameters: 无

   response: 

   ​        fail:

           ```
         {
            "status": 1,
            "msg": "获取失败"
         }
           ```

   ​        success:

   ```
         {
            "status": 0,
            "data": [
                 {
                     "categoryId": "DOGS",
                     "name": "Dogs",
                     "description":"<image src=\"../images/dogs_icon.gif\"><font                      size=\"5\" color=\"blue\"> Dogs</font>"
                 },
                 {...},
                 ...
            ]
         }
   ```

2.  根据id获得单个category类信息

   method: get

   url: /catalog/categories/{id}  如：/catalog/categories/DOGS

   parameters: 无

   response: 

   ​        fail:

   ```
         {
            "status": 1,
            "msg": "获取失败"
         }
   ```

   ​        success:

   ```
         {
            "status": 0,
            "data":  {
                   "categoryId": "DOGS",
                    "name": "Dogs",
                    "description":"<image src=\"../images/dogs_icon.gif\"><font                      size=\"5\" color=\"blue\"> Dogs</font>"
             }     
         }
   ```

3.  获得某个catagory下的所有product

   method: get

   url: /catalog/categories/{id}/products

   parameters: 无

   response: 

   ​        fail:

   ```
         {
            "status": 1,
            "msg": "获取失败"
         }
   ```

   ​        success:

   ```
         {
            "status": 0,
            "data": [
                {              
                   "productId": "K9-RT-01",
                   "categoryId": "DOGS",
                   "name": "Golden Retriever",
                   "description": "<image src=\"images/dog1.gif\">Great family                        dog"                  
                },
                {...},
                ...
            ]
         }
   ```

4.  获得所有的product

   method: get

   url:/catalog/products

   parameters: 无

   response: 

   ​        fail:

   ```
         {
            "status": 1,
            "msg": "获取失败"
         }
   ```

   ​        success:

   ```
         {
            "status": 0,
            "data": [
                {              
                   "productId": "K9-RT-01",
                   "categoryId": "DOGS",
                   "name": "Golden Retriever",
                   "description": "<image src=\"images/dog1.gif\">Great family                        dog"                  
                },
                {...},
                ...
            ]
         }  
   ```

5. 根据关键字获得product

   method: get

   url:/catalog/products

   parameters:    keywords

   response: 

   ​        fail:

   ```
         {
            "status": 1,
            "msg": "获取失败"
         }
   ```

   ​        success:

   ```
         {
            "status": 0,
            "data": [
                {              
                   "productId": "K9-RT-01",
                   "categoryId": "DOGS",
                   "name": "Golden Retriever",
                   "description": "<image src=\"images/dog1.gif\">Great family                        dog"                  
                },
                {...},
                ...
            ]
         }  
   ```

6. 根据id获得单个product

   method: get

   url:/catalog/products/{id}   如/catalog/products/K9-RT-01

   parameters: 无

   response: 

   ​        fail:

   ```
         {
            "status": 1,
            "msg": "获取失败"
         }
   ```

   ​        success:

   ```
         {
            "status": 0,
            "data": {              
                   "productId": "K9-RT-01",
                   "categoryId": "DOGS",
                   "name": "Golden Retriever",
                   "description": "<image src=\"images/dog1.gif\">Great family                        dog"                  
            }
         }  
   ```

7.  获得某个product下的所有item

   method: get

   url:/catalog/products/{id}/items   如/catalog/products/K9-RT-01/items

   parameters: 无

   response: 

   ​        fail:

   ```
         {
            "status": 1,
            "msg": "获取失败"
         }
   ```

   ​        success:

   ```
         {
            "status": 0,
            "data": [
                {
                    "itemId": "EST-28",
                    "productId": "K9-RT-01",
                    "listPrice": "155.29",
                    "unitCost": "90.00",
                    "supplierId": "1",
                    "status": "P",
                    "attribute1": "Adult Female",
                    "attribute2": "",
                    "attribute3": "",
                    "attribute4": "",
                    "attribute5": "",
                    "product":{
                        "productId": "K9-RT-01",
                        "categoryId": "DOGS",
                        "name": "Golden Retriever",
                        "description": "<image src=\"images/dog1.gif\">Great                              family dog"          
                    },
                    "quantity": "10000"
                },
                {...},
                ...
            ]
         }  
   ```

8.  根据id获得单个item

   method: get

   url:/catalog/items/{id}   如/catalog/K9-RT-01

   parameters: 无

   response: 

   ​        fail:

   ```
         {
            "status": 1,
            "msg": "获取失败"
         }
   ```

   ​        success:

   ```
         {
            "status": 0,
            "data": {
                "itemId": "EST-28",
                "productId": "K9-RT-01",
                "listPrice": "155.29",
                "unitCost": "90.00",
                "supplierId": "1",
                "status": "P",
                "attribute1": "Adult Female",
                "attribute2": "",
                "attribute3": "",
                "attribute4": "",
                "attribute5": "",
                "product":{
                    "productId": "K9-RT-01",
                    "categoryId": "DOGS",
                    "name": "Golden Retriever",
                    "description": "<image src=\"images/dog1.gif\">Great                              family dog"          
                },
                "quantity": "10000"
            }  
         }  
   ```

#### 二、账户模块

   1.  用户名和密码登陆

      method: post

      url: /account/loginByUsername

      parameters: username, password

      response:

      ​        fail:

      ```
            {
               "status": 1,
               "msg": "登陆失败"
            }
      ```

      ​       success:

      ```
            {
               "status": 0,
               "msg": "登陆成功",
               "data":{
                   "username": "",
                   "password": "",
                   "emial": "",
                   "firstName": "",
                   "lastName": "",
                   "status": "",
                   "address1": "",
                   "address2": "",
                   "city": "",
                   "state": "",
                   "zip": "",
                   "country": "",
                   "phone": "",
                   "favouriteCategoryId": "",
                   "languagePreference": "",
                   "listOption": "",
                   "bannerOption": "",
                   "bannerName": ""
               }
            }
      ```

   2.  手机号和密码登陆

      method: post

      url: /account/loginByPhone

      parameters: phone, password

      response:

      ​        fail:

      ```
            {
               "status": 1,
               "msg": "登陆失败"
            }
      ```

      ​       success:

      ```
            {
               "status": 0,
               "msg": "登陆成功",
               "data":{
                   "username": "",
                   "password": "",
                   "emial": "",
                   "firstName": "",
                   "lastName": "",
                   "status": "",
                   "address1": "",
                   "address2": "",
                   "city": "",
                   "state": "",
                   "zip": "",
                   "country": "",
                   "phone": "",
                   "favouriteCategoryId": "",
                   "languagePreference": "",
                   "listOption": "",
                   "bannerOption": "",
                   "bannerName": ""
               }
            }
      ```

   3.  注册（要求绑定手机号，且要求用户名和手机号都不是已经用过的 需要前端验证验证码是否正确）

      method: post

      url: /account/register

      parameters: 

       ```
         {
             "username": "luotian123",
             "password": "1233456",
             "firstName": "luo",
             "lastName": "tian",
             "email": "2757334535@qq.com",
             "phone": "18607951748",
             "address1": "CSU",
             "address2": "CSE",
             "city": "",
             "state": "",
             "zip": "",
             "country": "",
             "phone": "",
             "favouriteCategoryId": "",
             "languagePreference": "",
             "listOption": "",
             "bannerOption": "",
         }
       ```

      response:

      ​        fail:

      ```
            {
               "status": 1,
               "msg": "注册失败！用户名已存在"
            }
      ```

      ​       success:

      ```
            {
               "status": 0,
               "msg": "注册成功！"
            }
      ```

   4.  根据用户id获得用户账号

   5.  根据电话获得用户账号

   6.  向电话号码发送验证码

   7.  根据电话号码修改密码

   8.  登陆之后在个人信息模块更新账户信息 电话号码不可修改

#### 三、购物车模块

#### 四、 订单模块 