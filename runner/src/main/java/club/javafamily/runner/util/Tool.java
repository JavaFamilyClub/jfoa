package club.javafamily.runner.util;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Tool {
   public static final String PROJECT_MAIN = "JavaFamily";
   public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
   public static final String DEFAULT_TIME_ZONE = "GMT+8";

   private static final String NOCLONE = new String("noclone");
   private static Map<String, Object> cloneFns = new ConcurrentHashMap<>();

   private static final SecureRandom secureRandom;

   private static final Logger LOG = LoggerFactory.getLogger(Tool.class);

   static {
      SecureRandom random = null;

      if(!SystemUtils.IS_OS_WINDOWS) {
         try {
            random = SecureRandom.getInstance("NativePRNGNonBlocking");
         }
         catch(NoSuchAlgorithmException ignore) {
         }
      }

      if(random == null) {
         try {
            random = SecureRandom.getInstance("SHA1PRNG");
         }
         catch(NoSuchAlgorithmException ignore) {
         }
      }

      if(random == null) {
         Random seedRandom = new Random(System.currentTimeMillis());
         byte[] seed = new byte[16];
         seedRandom.nextBytes(seed);
         random = new SecureRandom(seed);
      }

      secureRandom = random;
   }

   /**
    * Get the operator to use when concatenating an op parameter to a servlet
    * name.
    */
   public static String getConcat(String servletName) {
      return servletName.indexOf('?') >= 0 ? "&" : "?";
   }

   /**
    * Deep clone a collection.
    * <p>
    * For any value in the collection, if it's an instance of Cloneable, and
    * contains a public clone method, the value will be cloned, otherwise the
    * cloned collection will use the value directly.
    *
    * @param from the to be cloned collection.
    * @return the cloned collection, null means the to be cloned list is null,
    * or exception occurs.
    */
   @SuppressWarnings("unchecked")
   public static <T extends Collection> T deepCloneCollection(T from) {
      if(from == null) {
         return null;
      }

      try {
         T to = (T) from.getClass().newInstance();
         Iterator values = from.iterator();

         while(values.hasNext()) {
            Object val = values.next();

            if(val == null) {
               to.add(null);
            }

            else if(val instanceof Cloneable) {
               Method m = getClone(val.getClass());

               if(m == null) {
                  to.add(val);
               }
               else {
                  to.add(m.invoke(val));
               }
            }
            else {
               to.add(val);
            }
         }

         return to;
      }
      catch(Exception ex) {
         LOG.error("Failed to deepClone object: " + from, ex);
         return null;
      }
   }

   /**
    * Deep clone a map.
    * <p>
    * keys of the map will not be cloned. For any value, if it's an instance of
    * Cloneable, and contains a public clone method, the value will be cloned,
    * otherwise the cloned map will use the value directly.
    *
    * @param from the to be cloned map.
    * @return the cloned map, null means the to be cloned map is null,
    * or exception occurs.
    */
   @SuppressWarnings("unchecked")
   public static <K, V> Map<K, V> deepCloneMap(Map<K, V> from) {
      if(from == null) {
         return null;
      }

      try {
         Map<K, V> to = (Map<K, V>) from.getClass().newInstance();

         for(Map.Entry<K, V> entry : from.entrySet()) {
            K key = entry.getKey();
            V val = entry.getValue();

            if(val == null) {
               to.put(key, null);
            }
            else if(val instanceof Cloneable) {
               Method m = getClone(val.getClass());

               if(m == null) {
                  to.put(key, val);
               }
               else {
                  to.put(key, (V) m.invoke(val));
               }
            }
            else {
               to.put(key, val);
            }
         }

         return to;
      }
      catch(Exception ex) {
         LOG.error("Failed to deep clone map: " + from, ex);
         return null;
      }
   }

   /**
    * Util clone method.
    */
   public static Object clone(Object v) {
      if(v instanceof Collection) {
         return deepCloneCollection((Collection) v);
      }
      else if(v instanceof Map) {
         return deepCloneMap((Map) v);
      }
      else if(v instanceof Object[][]) {
         Object[][] src = (Object[][]) v;

         if(src.length <= 0 || src[0].length <= 0) {
            return src;
         }

         Object[][] target = (Object[][]) Array.newInstance(
            getItemComponentType(src), new int[] {src.length,
               src.length > 0 ? src[0].length : 0});

         for(int i = 0; i < target.length; i++) {
            target[i] = (Object[]) clone(src[i]);
         }
      }
      else if(v instanceof Object[]) {
         Object[] src = (Object[]) v;

         if(src.length <= 0) {
            return src;
         }

         Object[] target = (Object[]) Array.newInstance(
            getItemComponentType(src), src.length);

         for(int i = 0; i < src.length; i++) {
            target[i] = clone(src[i]);
         }

         return target;
      }
      else if(v instanceof Cloneable) {
         Method cm = getClone(v.getClass());

         try {
            if(cm != null) {
               return cm.invoke(v);
            }
         }
         catch(Exception ex) {
            // ignore it
         }
      }

      return v;
   }

   private static Method getClone(Class cls) {
      String key = cls.getName();
      Object cm = cloneFns.get(key);

      if(cm == NOCLONE) {
         return null;
      }

      try {
         if(cm == null) {
            cm = cls.getMethod("clone");
            cloneFns.put(key, cm);
         }
      }
      catch(Exception ex) {
         cloneFns.put(key, NOCLONE);
      }

      return (Method) cm;
   }

   /**
    * Get the item type of an array or multi-dimensional array.
    */
   private static Class getItemComponentType(Object arr) {
      Class cls = arr.getClass();

      while(cls.isArray()) {
         cls = cls.getComponentType();
      }

      return cls;
   }

   public static String getUserHome() {
      String userHome = System.getProperty("user.home");

      return userHome + "/.jfoa";
   }

   public static File getCacheDir() {
      File file = new File(Tool.getUserHome(), CACHE_DIR);

      if(!file.exists() || !file.isDirectory()) {
         if(file.mkdirs()) {
            LOG.info("Auto create cache dir: " + file.getAbsolutePath());
         }
      }

      return file;
   }

   private static final String CACHE_DIR = "/cache";
   public static SecureRandom getSecureRandom() {
      return secureRandom;
   }
}
