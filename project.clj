(defproject clojure-parallel-runs "1.0.1-SNAPSHOT"
  :description "Simple job distribution through RabbitMQ"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
		 [clj-serializer "0.1.1"]
                 [kephale/rabbitcj "0.1.1-SNAPSHOT"]]
  :main clojure-parallel-runs.core)
