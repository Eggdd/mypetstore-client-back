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

         {
            "status": 1,
            "msg": "获取失败"
         }
   
   ​        success:

   ```
      {
            "status": 0,
            "data": [
                 {
                     "categoryId": "DOGS",
                     "name": "Dogs",
                     "description":"<image src=\"../images/dogs_icon.gif\"><font size=\"5\" color=\"blue\"> Dogs</font>"
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
                    "description":"<image src=\"../images/dogs_icon.gif\"><font size=\"5\" color=\"blue\"> Dogs</font>"
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
                   "description": "<image src=\"images/dog1.gif\">Great family dog"                  
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
                   "description": "<image src=\"images/dog1.gif\">Great family dog"                  
                },
                {...},
                ...
            ]
         }  
   ```

5. 根据关键字获得product

   method: get

   url:/catalog/products/keywards

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
                   "description": "<image src=\"images/dog1.gif\">Great family dog"                  
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
                   "description": "<image src=\"images/dog1.gif\">Great family dog"                  
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
                    "quantity": "10000",
                    "product": {
                        "productId": "K9-RT-01",
                        "categoryId": "DOGS",
                        "name": "Golden Retriever",
                        "description": "<image src=\"images/dog1.gif\">Great family dog" 
                    }   
                },
                {...},
                ...
            ]
         }  
   ```
   
8.  根据itemId获得单个item

   method: get

   url:/catalog/items/{id}   如/catalog/items/EST-11

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
                "quantity": "10000",
                "product": {
                    "productId": "K9-RT-01",
                    "categoryId": "DOGS",
                    "name": "Golden Retriever",
                    "description": "<image src=\"images/dog1.gif\">Great family dog" 
                }   
            }  
         }  
   ```

#### 二、账户模块

   1.  用户名和密码登陆

      method: post

      url: /accounts/loginByUsername

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
                   "username": "111",
                   "password": "",    // 密码屏蔽掉了 不给前端
                   "emial": "111",
                   "firstName": "a",
                   "lastName": "a",
                   "status": "aa",
                   "address1": "aa",
                   "address2": "a",
                   "city": "a",
                   "state": "a",
                   "zip": "a",
                   "country": "a",
                   "phone": "a",
                   "favouriteCategoryId": "a",
                   "languagePreference": "a",
                   "listOption": "true",
                   "bannerOption": "true",
                   "bannerName": "xxxxxx"
               }
            }
      ```

   2.  手机号和验证码登陆 （要求前端自行调用方法发送验证码和判断验证码正确性，参考第5条）

      method: post

      url: /accounts/loginByPhone

      parameters: phone

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
                   "username": "1",
                   "password": "",  // 屏蔽掉了
                   "emial": "1",
                   "firstName": "1",
                   "lastName": "1",
                   "status": "1",
                   "address1": "1",
                   "address2": "1",
                   "city": "1",
                   "state": "1",
                   "zip": "1",
                   "country": "1",
                   "phone": "18607951748",
                   "favouriteCategoryId": "1",
                   "languagePreference": "1",
                   "listOption": "true",
                   "bannerOption": "true",
                   "bannerName": "1"
               }
            }
      ```

   3.  注册（要求绑定手机号，且要求用户名和手机号都不是已经用过的 需要前端验证验证码是否正确）

      method: post

      url: /accounts/register

      parameters: 

       ```
         {
             "username": "luotian123",    // 要求非空
             "password": "1233456",       // 非空
             "firstName": "luo",
             "lastName": "tian",
             "email": "2757334535@qq.com",
             "phone": "18607951748",     // 要求非空且不重复
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
             "bannerOption": ""
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

   4. 根据用户id获得用户账号 (用来注册的时候判断用户名是否已注册)

      method: get

      url: /accounts/{username}  如 /accounts/j2ee

      parameters:  无

      response:

      ​        fail:

      ```
            {
               "status": 1,
               "msg": "用户不存在！"
            }
      ```

      ​       success:

      ```
            {
               "status": 0,
               "data": {
                   "username": "luotian123",
                   "password": "",   // 屏蔽
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
            }
      ```

   5. 根据电话获得用户账号 （用来注册的时候判断手机号是否已经被注册）

      method: get

      url: /accounts/phones

      parameters:  phone

      response:

      ​        fail:

      ```
            {
               "status": 1,
               "msg": "用户不存在！"
            }
      ```

      ​       success:

      ```
            {
               "status": 0,
               "data": {
                   "username": "luotian123",
                   "password": "",   // 屏蔽
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
            }
      ```

   6. 向电话号码发送验证码(后端向电话号码发送验证码，并把验证码发回给前端验证)

      method: post

      url: /accounts/sendMsg

      parameters:  phone

      response:

      ​        fail:

      ```
            {
               "status": 1,
               "msg": "发送失败！电话号码不存在"
            }
      ```

      ​       success:

      ```
            {
               "status": 0,
               "data": "1234"
            }
      ```

   7. 根据电话号码修改密码（需要发送验证码且验证码正确 由前端保证）

      method: post

      url: /accounts/modifyPassword

      parameters:  phone, password

      response:

      ​        fail:

      ```
            {
               "status": 1,
               "msg": "更改失败"
            }
      ```

      ​       success:

      ```
            {
               "status": 0,
               "msg": "修改成功"
            }
      ```

   8. 登陆之后在个人信息模块更新账户信息 电话号码不可修改

      method: put

      url: /accounts/{username}

      parameters: 

      ```
          {
              "username": "j2ee",
              "password": "",      // 这个在这个方法也是不可以修改 不用传
              "emial": "123@qq.com",
              "firstName": "john",
              "lastName": "xxx",
              "address1": "csu",
              "address2": "cse",
              "city": "changsha",
              "state": "sss",
              "zip": "ss",
              "country": "China",
              "phone": "18607951748",    // 不可更改，但是还是把原来的传过来
              "favouriteCategoryId": "BIRDS",
              "languagePreference": "Chinese",
              "listOption": "true",
              "bannerOption": "true"
          }
      ```

      response:

      ​        fail:

      ```
            {
               "status": 1,
               "msg": "更改成功"
            }
      ```

      ​       success:

      ```
            {
               "status": 0,
               "msg": "更改失败"
            }
      ```

9.  更换绑定的电话号码  更换之前需要客户端向新电话号码发送验证码并验证

   method: put

   url: /accounts/{username}/changePhone

   parameters:    phone

   response:

   ​        fail:

   ```
         {
            "status": 1,
            "msg": "更改成功"
         }
   ```

   ​       success:

   ```
         {
            "status": 0,
            "msg": "更改失败"
         }
   ```

10. 

#### 三、购物车模块

1.  添加商品到购物车（每次添加一个）

   method: post

   url: /accounts/{username}/carts/{itemId}

   parameters:  无

   response:

   ​        fail:

   ```
         {
            "status": 1,
            "msg": "添加失败"
         }
   ```

   ​       success:

   ```
         {
            "status": 0,
            "msg": "添加成功！"
         }
   ```

2.  更新购物车

   method: put

   url: /accounts/{username}/carts

   parameters: itemId(如  "EST-1"),   quantity(如  8)

   response:
   
   ​        fail:
   
   ```
         {
         "status": 1,
            "msg": "更新失败"
      }
   ```

   ​       success:
   
   ```
         {
            "status": 0,
            "msg": "添加成功！"
      }
   ```

3.  获得某个账号下的所有购物车

   method: get

   url: accounts/{username}/carts

   parameters:  无

   response:

   ​        fail:

   ```
         {
            "status": 1,
            "msg": "获得失败"
         }
   ```

   ​       success:

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
                    "quantity": "10000",
                    "product": {
                        "productId": "K9-RT-01",
                        "categoryId": "DOGS",
                        "name": "Golden Retriever",
                        "description": "<image src=\"images/dog1.gif\">Great family dog" 
                    },        // 也就是一个item对象
                    "quantity": 1,   // 这个quantity是指购物车中该商品的数量
                    "inStock": true,
                    "total": 155.29    // 单价*数量
                },
                {...},
                ...
            ]
         }
   ```

4.  移除购物车的某项

   method: delete

   url: accounts/{username}/carts/{itemId}

   parameters:  无

   response:

   ​        fail:

   ```
         {
            "status": 1,
            "msg": "删除失败"
         }
   ```

   ​       success:

   ```
         {
            "status": 0,
            "msg": "删除成功！"
         }
   ```

#### 四、 订单模块 

1.  增加订单

   method: post

   url: /orders

   parameters: 

   ```
      {
          "username": "luotian",
          "orderDate": "2022-01-12",
          "shipAddress1": "CSU",
          "shipAddress2": "CSE",
          "shipCity": "changsha",
          "shipState": "11",
          "shipZip": "abc",
          "shipCountry": "China",
          "billAddress1": "CSU",
          "billAddress2": "USC",
          "billCity": "abb",
          "billState": "abbb",
          "billZip": "www",
          "billCountry": "China",
          "courier": "aaaa",
          "totalPrice": 250.00,
          "billToFirstName": "luo",
          "billToLastName": "tian",
          "shipToFirstName": "luo",
          "shipToLastName": "tian",
          "creditCard": "888 888 888",
          "expiryDate": "12/03",
          "cardType": "Visa",
          "locale": "CA",
          "lineItems": [
               {
                   "itemId": "EST-1",
                   "quantity": "2"
               },
               {...},
               ...
          ]
      }
   ```

   response:

   ​        fail:

   ```
         {
            "status": 1,
            "msg": "添加失败"
         }
   ```

   ​       success:

   ```
         {
            "status": 0,
            "msg": "添加成功！"
         }
   ```

2. 根据订单id获得订单

   method: get

   url: /orders/{id}

   parameters:  无

   response:

   ​        fail:

   ```
         {
            "status": 1,
            "msg": "获取失败"
         }
   ```

   ​       success:

   ```
         {
            "status": 0,
            "data": {
                “orderId": 1006,
                "username": "luotian",
                "orderDate": "2022-01-12",
                "shipAddress1": "CSU",
                "shipAddress2": "CSE",
                "shipCity": "changsha",
                "shipState": "11",
                "shipZip": "abc",
                "shipCountry": "China",
                "billAddress1": "CSU",
                "billAddress2": "USC",
                "billCity": "abb",
                "billState": "abbb",
                "billZip": "www",
                "billCountry": "China",
                "courier": "aaaa",
                "totalPrice": 250.00,
                "billToFirstName": "luo",
                "billToLastName": "tian",
                "shipToFirstName": "luo",
                "shipToLastName": "tian",
                "creditCard": "888 888 888",
                "expiryDate": "12/03",
                "cardType": "Visa",
                "locale": "CA",
                "lineItems": [
                    {
                        "orderId": 1006,
                        "lineNumber": 1002,   // 上面这两项对于前端没有用，忽略
                        "itemId": "EST-1",
                        "quantity": "2",
                        "unitPrice": 16.5   // item的listPrice
                        "total": 33      // quantity*unitPrice
                        "item": {
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
                            "quantity": "10000",
                            "product": {
                                "productId": "K9-RT-01",
                                "categoryId": "DOGS",
                                "name": "Golden Retriever",
                                "description": "<image src=\"images/dog1.gif\"> Great family dog"           
                           }
                        }   // 一个item对象
                    }      // 一个lineItem对象 
            }   // 一个order对象
         }
   ```

3. 查询某个用户的所有订单

   method: get

   url: /accounts/{username}/orders

   parameters:  无

   response:

   ​        fail:

   ```
         {
            "status": 1,
            "msg": "获取失败"
         }
   ```

   ​       success:

   ```
         {
            "status": 0,
            "data": [
                { 
                    “orderId": 1006,
                    "username": "luotian",
                    "orderDate": "2022-01-12",
                    "shipAddress1": "CSU",
                    "shipAddress2": "CSE",
                    "shipCity": "changsha",
                    "shipState": "11",
                    "shipZip": "abc",
                    "shipCountry": "China",
                    "billAddress1": "CSU",
                    "billAddress2": "USC",
                    "billCity": "abb",
                    "billState": "abbb",
                    "billZip": "www",
                    "billCountry": "China",
                    "courier": "aaaa",
                    "totalPrice": 250.00,
                    "billToFirstName": "luo",
                    "billToLastName": "tian",
                    "shipToFirstName": "luo",
                    "shipToLastName": "tian",
                    "creditCard": "888 888 888",
                    "expiryDate": "12/03",
                    "cardType": "Visa",
                    "locale": "CA",
                    "lineItems": [
                        {
                            "orderId": 1006,
                            "lineNumber": 1002,   // 上面这两项对于前端没有用，忽略
                            "itemId": "EST-1",
                            "quantity": "2",
                            "unitPrice": 16.5   // item的listPrice
                            "total": 33      // quantity*unitPrice
                            "item": {
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
                                "quantity": "10000",
                                "product": {
                                    "productId": "K9-RT-01",
                                    "categoryId": "DOGS",
                                    "name": "Golden Retriever",
                                    "description": "<image src=\"images/dog1.gif\"> Great family dog"           
                               }
                            }   // 一个item对象
                        },      // 一个lineItem对象 
                        {...},
                        ...
                    ]    // lineItem数组
                },   // 一个order对象
                {...},
                ...
            ]    // 一个order对象数组
         }
   ```

