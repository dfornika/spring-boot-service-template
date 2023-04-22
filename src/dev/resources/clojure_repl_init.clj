(in-ns 'user)
(require
    '[clojure.reflect :refer [reflect]]
    '[clojure.repl :refer [doc]]
    '[clojure.string :as s]
    '[nrepl.server :as n]
    '[cider.nrepl :refer (cider-nrepl-handler)])

;; Provide an alias since we are going to use `bean` for Spring:
;; (we need the value to be fn? and to have the bean's docstring; I don't know of a better way:)
#_(intern 'user (with-meta 'jb    (meta #'clojure.core/bean)) clojure.core/bean)
#_(intern 'user (with-meta 'jbean (meta #'clojure.core/bean)) clojure.core/bean)

;; Helper functions
(defn help
  "List our helper functions (and vars)"
  []
  (println "Helper functions available in the user namespace:")
  (->> (vals (ns-publics 'user))
       (filter #(fn? (deref %)))
       (map #(let [{:keys [name doc]} (meta %)]
               (str "* " name (if doc (str " :- " doc) ""))))
       (sort)
       (s/join "\n")
       (println))
  (println "\nYou can also use `(doc a-fn)` and `(reflect an-object)`.")
  (println "Remember that *1 holds the result of the last call and *e the last error."))

(defn list-spring-beans
  "List all Spring Beans; ex: `(list-spring-beans)`"
  []
  (seq (.getBeanDefinitionNames user/spring-app-ctx)))

(defn find-bean
  "List all Spring bean names containing the given substring (case-insensitive)"
  [substring]
  (filter
    #(re-matches
       (re-pattern
         (str "(?i).*" substring ".*")) %)
    (list-spring-beans)))

(defn spring-bean
  "Get Spring Bean by a name (from (list-beans)); ex: `(bean \"configService\")`"
  [name]
  (.getBean user/spring-app-ctx name))

(defn members
  "Show public methods, fields of a bean; ex: `(members aBean)`"
  [bean]
  (->> bean clojure.reflect/reflect :members (filter (comp :public :flags)) (map :name)))

(defonce server (n/start-server :port user/nrepl-port :handler cider-nrepl-handler))

(defn stop-repl-server
  "Called from ClojureReplServer upon exit; don't use directly"
  []
  (n/stop-server server))

(defn reset
  "Reset the pre-defined functions and vars in the case that you messed up with them. Does not remove vars you made (we'd need clojure.tools/refresh for that)."
  []
  (.reset user/nrepl-server))