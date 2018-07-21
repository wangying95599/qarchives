## 关键字定义

- GET (选择)：从服务器上获取一个具体的资源或者一个资源列表。
- POST （创建）： 在服务器上创建一个新的资源。
- PUT （更新）：以整体的方式更新服务器上的一个资源。
- PATCH （更新）：只更新服务器上一个资源的一个属性。
- DELETE （删除）：删除服务器上的一个资源。

还有两个不常用的HTTP动词：

- HEAD ： 获取一个资源的元数据，如数据的哈希值或最后的更新时间。
-  OPTIONS：获取客户端能对资源做什么操作的信息。
一个好的RESTful API只允许第三方调用者使用这四个半HTTP动词进行数据交互，并且在URL段里面不出现任何其他的动词。

一般来说，GET请求可以被浏览器缓存（通常也是这样的）。例如，缓存请求头用于第二次用户的POST请求。HEAD请求是基于一个无响应体的GET请求，并且也可以被缓存的。

## API模版
一个端点就是指向特定资源或资源集合的URL。

如果你正在构建一个虚构的API来展现几个不同的动物园，每一个动物园又包含很多动物，员工和每个动物的物种，你可能会有如下的端点信息：

https://api.example.com/v1/zoos
https://api.example.com/v1/animals
https://api.example.com/v1/animal_types
https://api.example.com/v1/employees
针对每一个端点来说，你可能想列出所有可行的HTTP动词和端点的组合。如下所示，请注意我把HTTP动词都放在了虚构的API之前，正如将同样的注解放在每一个HTTP请求头里一样。（下面的URL就不翻译了，我觉得没啥必要翻^_^）

- GET /zoos: List all Zoos (ID and Name, not too much detail)
- POST /zoos: Create a new Zoo
- GET /zoos/ZID: Retrieve an entire Zoo object
- PUT /zoos/ZID: Update a Zoo (entire object)
- PATCH /zoos/ZID: Update a Zoo (partial object)
- DELETE /zoos/ZID: Delete a Zoo
- GET /zoos/ZID/animals: Retrieve a listing of Animals (ID and Name).
- GET /animals: List all Animals (ID and Name).
- POST /animals: Create a new Animal
- GET /animals/AID: Retrieve an Animal object
- PUT /animals/AID: Update an Animal (entire object)
- PATCH /animals/AID: Update an Animal (partial object)
- GET /animal_types: Retrieve a listing (ID and Name) of all Animal Types
- GET /animal_types/ATID: Retrieve an entire Animal Type object
- GET /employees: Retrieve an entire list of Employees
- GET /employees/EID: Retreive a specific Employee
- GET /zoos/ZID/employees: Retrieve a listing of Employees (ID and Name) who work at this Zoo
- POST /employees: Create a new Employee
- POST /zoos/ZID/employees: Hire an Employee at a specific Zoo
- DELETE /zoos/ZID/employees/EID: Fire an Employee from a specific Zoo
在上面的列表里，ZID表示动物园的ID， AID表示动物的ID，EID表示雇员的ID，还有ATID表示物种的ID。让文档里所有的东西都有一个关键字是一个好主意。

## 分页查询

- ?limit=10: 减少返回给客户端的结果数量（用于分页）
- ?offset=10: 发送一堆信息给客户端（用于分页）
- ?animal_type_id=1: 使用条件匹配来过滤记录
- ?sortby=name&order=asc:  对结果按特定属性进行排序


## 响应标准

- 200 OK – `[GET]`
    The Consumer requested data from the Server, and the Server found it for them (Idempotent)
    客户端向服务器请求数据，服务器成功找到它们
- 201 CREATED – `[POST/PUT/PATCH]`
    The Consumer gave the Server data, and the Server created a resource
    客户端向服务器提供数据，服务器根据要求创建了一个资源
- 204 NO CONTENT – `[DELETE]`
    The Consumer asked the Server to delete a Resource, and the Server deleted it
    客户端要求服务器删除一个资源，服务器删除成功
-  400 INVALID REQUEST – `[POST/PUT/PATCH]`
    The Consumer gave bad data to the Server, and the Server did nothing with it (Idempotent)
    客户端向服务器提供了不正确的数据，服务器什么也没做
- 404 NOT FOUND – `[*]`
    The Consumer referenced an inexistant Resource or Collection, and the Server did nothing (Idempotent)
    客户端引用了一个不存在的资源或集合，服务器什么也没做
- 500 INTERNAL SERVER ERROR – `[*]`
    The Server encountered an error, and the Consumer has no knowledge if the request was successful
    服务器发生内部错误，客户端无法得知结果，即便请求已经处理成功
    
  应该加上 -202 ACCEPT 客户端请求修改 服务器成功修改