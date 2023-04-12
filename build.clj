(ns build
  (:refer-clojure :exclude [test])
  (:require [org.corfield.build :as bb]))

(def ^:private lib 'com.flexiana/clj-openai)
(def ^:private version "0.1.0")

(defn ci
  "Run the CI pipeline of tests (and build the JAR)."
  [opts]
  (-> opts
      (assoc :lib lib
             :version version)
      (bb/run-task [:kondo])
      (bb/run-task [:eastwood])
      (bb/run-task [:coverage])
      bb/run-tests
      bb/clean
      bb/jar))

(defn local-install
  "Install the JAR locally."
  [opts]
  (-> opts
      (assoc :lib lib
             :version version)
      bb/install))

(defn deploy
  "Deploy the JAR to Clojars."
  [opts]
  (-> opts
      (assoc :lib lib
             :version version)
      bb/deploy))
