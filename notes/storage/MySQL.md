| 输入                            | 作用               |
| ------------------------------- | ------------------ |
| show databases                  | 数据库列           |
| use （数据库）                  | 切换数据库         |
| show tables                     | 数据库的表         |
| show columns from （表名）      | 显示的所有列信息   |
| show create database （数据库） | 显示数据库创建语句 |
| show create table （表名）      | 显示表的创建语句   |


## 检索数据 SELECT
> 如果没有明确排序查询结果，则返回的数据的顺序没有特殊意义。 

| 输入                                        | 作用                                                   |
| ------------------------------------------- | ------------------------------------------------------ |
| SELECT phone FROM tb_user;                  | 查询表tb_user中phone列的所有数据                       |
| SELECT phone,name FROM tb_user;             | 可以同时查询多列，用逗号隔开                           |
| SELECT * FROM tb_user;                      | 查询所有列                                             |
| SELECT DISTINCT phone from tb_user;         | 检索不同的行，重复的不显示。多列情况需全部不同才不显示 |
| SELECT phone FROM tb_user LIMIT 1;          | 限制最多输出行数                                       |
| SELECT phone FROM tb_user LIMIT 1,3;        | 限制从第一行开始的三行                                 |
| SELECT phone FROM tb_user LIMIT 3 OFFSET 1; | 限制从第一行开始的三行                                 |

## 排序检索数据
> 默认的检索排序是从小到大排序的

| 输入                                                         | 作用                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| SELECT phone FROM tb_user ORDER BY phone;                    | 查询按phone进行排序，也可以多个                              |
| SELECT phone FROM tb_user ORDER BY phone DESC;               | 从大到小排序，多个数据只应用到前面的那个数，而后面的为默认。所以可以指定多个 |
| SELECT name,phone FROM tb_user ORDER BY phone,name DESC;     |                                                              |
| SELECT name,phone FROM tb_user ORDER BY phone DESC,name ASC; |                                                              |
| SELECT name,phone FROM tb_user ORDER BY phone LIMIT 1;       | 限制语句应该放在最后                                         |
