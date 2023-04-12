(ns clj-openai.core-test
  (:require [cheshire.core :as json]
            [clj-http.fake :refer [with-fake-routes]]
            [clj-openai.core :refer [chat-completions completions]]
            [clojure.test :refer [deftest testing is]]))

(deftest completion
  (testing "returns message extracted from body on success"
    (with-fake-routes {"https://api.openai.com/v1/completions"
                         {:post (fn [_]
                                  {:status 200,
                                   :body (json/generate-string
                                           {:choices [{:text "foo"}]})})}}
                      (is (= "foo" (completions {:api-key "key"} "bar")))))
  (testing "returns whole body on failure"
    (with-fake-routes
      {"https://api.openai.com/v1/completions"
         {:post (fn [_]
                  {:status 400, :body (json/generate-string {:error "foo"})})}}
      (is (= {:error "foo"} (completions {} "bar"))))))

(deftest chat-completion
  (testing "returns message extracted from body on success"
    (with-fake-routes
      {"https://api.openai.com/v1/chat/completions"
         {:post (fn [_]
                  {:status 200,
                   :body (json/generate-string
                           {:choices [{:message {:content "foo"}}]})})}}
      (is (= "foo" (chat-completions {:api-key "key"} "bar")))))
  (testing "returns whole body on failure"
    (with-fake-routes
      {"https://api.openai.com/v1/chat/completions"
         {:post (fn [_]
                  {:status 400, :body (json/generate-string {:error "foo"})})}}
      (is (= {:error "foo"} (chat-completions {} "bar"))))))
