(ns user
  (:require [clojure.core.protocols :as protocols]
            [clojure.java.data :as jdata]
            [clojure.datafy :refer [datafy]]))

(extend-protocol protocols/Datafiable
  org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext
  (datafy [o]
    (try (bean o)
         (catch UnsupportedOperationException e (println e)))))

(extend-protocol protocols/Datafiable
  org.springframework.web.context.support.GenericWebApplicationContext
  (datafy [o]
    (try (bean o)
         (catch UnsupportedOperationException e (println e)))))

(extend-protocol protocols/Datafiable
  org.springframework.boot.autoconfigure.web.servlet.MultipartProperties
  (datafy [o]
    (try (bean 0)
         (catch UnsupportedOperationException e (println e)))))

(defn get-environment []
  (-> spring-app-ctx
      bean
      :environment
      bean
      (update :systemEnvironment #(into {} %))
      (update :systemProperties #(into {} %))
      (update :conversionService bean )
      (update :propertySources bean)))


(defn get-servlet-context []
  (-> spring-app-ctx
      bean
      :servletContext
      bean
      (update :defaultSessionTrackingModes #(mapv bean %))
      (update :contextHandler bean)
      (update-in [:contextHandler :securityHandler] bean)
      (update :servletNames identity)
      (update :servletHandler identity)
      (update :attributeNameSet #(into #{} %))
      (update :attributeEntrySet #(into {} %))
      (update :attributes #(into {} %))
      (update :sessionCookieConfig bean)
      (update :classLoader #(update (bean %) :definedPackages (fn [x] (mapv bean x))))
      (update :effectiveSessionTrackingModes #(into #{} (map bean %)))
      (update :parent identity)
      (update-in [:contextHandler :beans] #(mapv bean %))
      #_(update :mimeTypes bean)
      ))


(comment
  (help)
  (pprint (list-spring-beans))
  (pprint (members (spring-bean "sequencingrunsSpringBootApplication")))
  (pprint (bean (spring-bean "resourceMappings")))
  (def simple-spring-app-keys [:id
                               :active
                               :class
                               :webserver
                               :servletContext
                               :startupDate
                               :protocolResolvers
                               :parent
                               :beanDefinitionCount
                               :displayName
                               :parentBeanFactory
                               :applicationName
                               :running
                               :environment])
  (select-keys (bean spring-app-ctx) simple-spring-app-keys)
  (pprint  (bean spring-app-ctx))
  (pprint (members spring-app-ctx))

  (pprint (members spring-app-ctx))
  (.getNamespace spring-app-ctx)

  (jdata/from-java spring-app-ctx)
  )
