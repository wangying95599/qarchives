package org.quetzaco.archives.model.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by dong on 2017/3/15.
 */
public class APIEntity<T> extends ResourceSupport {

  private final T model;

  @JsonCreator
  public APIEntity(@JsonProperty("model") T model) {
    this.model = model;
  }

  public T getContent() {
    return model;
  }

  public static <T> APIEntity<T> create(T model) {
    return new APIEntity(model);
  }

  public APIEntity<T> addLinkOn(Link... link) {
    add(link);
    return this;
  }

}
