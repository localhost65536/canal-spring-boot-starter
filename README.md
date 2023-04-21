# canal-spring-boot-starter
## SpringBoot3.0+JDK17+canal1.1.4 简易封装 

> 使用方式：

```yml
canal:
  mode: simple
  async: true
  server: txyun:11111
  destination: example
```

```java 
import cn.king.canal.client.spring.boot.framework.annotation.CanalTable;
import cn.king.canal.client.spring.boot.framework.handler.EntryHandler;
import cn.wisdom.api.model.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
* @author:
* @time: 2023-04-21 8:54
* @version: 1.0.0
* @description:
  */
  @Slf4j
  @Component
  @CanalTable("comment")
  public class CommentHandler implements EntryHandler<Comment> {

  @Override
  public void insert(Comment comment) {
  log.info("评论表中有binlog增加 insert comment: {}", comment);
  }

  @Override
  public void update(Comment before, Comment after) {
  log.info("评论表中有binlog更新 update comment: {} -> {}", before, after);
  }

  @Override
  public void delete(Comment comment) {
  log.info("评论表中有binlog删除 delete comment: {}", comment);
  }

}
```
