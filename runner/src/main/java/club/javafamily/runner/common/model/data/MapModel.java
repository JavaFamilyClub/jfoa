package club.javafamily.runner.common.model.data;

import org.springframework.util.CollectionUtils;

import java.util.*;

public class MapModel<T, V> {

   public MapModel() {
      this(null);
   }

   public MapModel(Map<T, V> map) {
      this.map = new ArrayList<>();

      if(CollectionUtils.isEmpty(map)) {
         return;
      }

      for(Map.Entry<T, V> entry : map.entrySet()) {
         this.map.add(new CommonsKVModel<>(entry.getKey(), entry.getValue()));
      }
   }

   public List<CommonsKVModel<T, V>> getMap() {
      return map;
   }

   public void setMap(List<CommonsKVModel<T, V>> map) {
      this.map = map;
   }

   private List<CommonsKVModel<T, V>> map;
}
