(ns clojure-parallel-runs.rabbitmq
  (:use [rabbitcj.client]
	[clj-serializer.core]))
;        [clojure.contrib.json]))

(def chan nil)

(defn init-connection
  "needs to be done once before other functions are used"
  [{:keys [host port user pass]}]
  (def chan (-> {:username user :password pass
                 :virtual-host "/" :host host :port port}
              (connect)
              (create-channel))))

(defn is-connection-open?
  "Is the connection open?"
  []
  (channel-open? chan))

(defn declare-and-bind-queues
  "Initialize queues with the given list of names."
  [& queue-names]
  (doseq [queue-name queue-names]
    (declare-exchange chan queue-name fanout-exchange false false nil)
    (declare-queue chan queue-name false false true nil)
    (bind-queue chan queue-name queue-name "")))

#_(defn get-one 
  "Get one message from the queue with the given name."
  [q] (try (read-json (String. (.. chan (basicGet q true) (getBody))) true)
	   (catch java.lang.Exception e (println e))))

(defn get-one
  "Get one message from the queue with the given name."
  [q]
  (try (deserialize (.. chan (basicGet q true) (getBody)) (Object.))
       (catch java.lang.Exception _ nil)))

#_(defn send-one
  "Get one message from the queue with the given name."
  [q i] (publish chan q "" (json-str i)))

(defn send-one [q i] (publish chan q "" (String. (serialize i))))