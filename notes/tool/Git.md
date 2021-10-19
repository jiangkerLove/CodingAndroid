- 用户配置

  ```bash
  git config --global user.name 'jiangfangping' 
  git config --global user.email 'jiangfangping@baijia.com'
  ```

  默认为 --local 只对某个仓库有效

  --global 对当前用户所有仓库有效

  --system 对系统所有登录的用户有效 

- 显示config的配置

  ```bash
  git config --list --global
  ```

  变更工作区的内容使用checkout，变更暂存区的内容使用reset

- 创建git仓库

  ```bash
  #把已有的项目代码纳入git管理
  git init               
  #创建一个空的git仓库
  git init name            
  ```

- 添加所有文件到暂存区

  ```bash
  git add . 
  ```

- 添加所有跟踪了有变更的文件到暂存区

  ```bash
  git add -u                              
  ```

- 删除文件 

  ```bash
  git rm .         
  ```

- 重命名文件

  ```bash
  git mv oldName newName                              
  ```

- 放弃暂存区的变更(变更回到工作区)

  ```bash
  git reset HEAD                             
  ```

- 放弃暂存区的变更和本地变更

   ```bash
   git reset --hard         
   ```

- 查看日志

  ```bash
  git log n4 --oneline
  ```

- 查看所有仓库日志

  ```bash
  git log --all --oneline --graph
  ```

- 查看分支

  ```bash
  git branch -av
  ```

- 创建分支

  ```bash
  git checkout -b temp
  ```

- 比较分支差异(比较head和head父亲的父亲commit的区别)

  ```bash
   git diff HEAD HEAD~2 git diff HEAD HEAD^^ 
  ```

- 删除分支

  ```bash
  git branch -d name //如果是没有合并到别的分支，会删除失败 
  git branch -D name //强制删除
  ```

- 修改最近提交的commit的msg

  ```bash
  git commit --amend 
  ```

- 对git分支进行变基，修改合并前面的commit

  ```bash
  git rebase -i eb7fa8（父commit）
  ```

- 查看暂存区和HEAD的差异

  ```bash
  git diff --cached              
  ```

- 查看工作区和暂存区的差异

  ```bash
  git diff git diff -- fileName //只查看指定文件              
  ```

- 暂存区恢复到工作区

  ```bash
  git checkout -- fileName
  ```

- stash操作

  ```bash
  git stash //存放
  git stash list //stash列表
  git stash pop //弹出顶部
  git stash apply //应用顶部但不弹出            
  ```

- 添加远端仓库

  ```bash
  git remote add <name> <path>
  ```

- 删除远程分支

  ```bash
  git push <name> --delete <branch-name>
  ```

- 清空本地有的远程分支而远程已不存在

  ```bash
  git remote prune <origin>
  ```