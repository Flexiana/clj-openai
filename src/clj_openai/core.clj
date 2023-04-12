(ns clj-openai.core
  (:require [clj-http.client :as http]))

(defn- post-request
  [{:keys [api-key base-url]} url-path body]
  (let [base-url (or base-url "https://api.openai.com/v1")
        headers {"Authorization" (str "Bearer " api-key),
                 "Content-Type" "application/json"}
        options {:headers headers,
                 :form-params body,
                 :content-type :json,
                 :as :json,
                 :coerce :always,
                 :throw-exceptions false}]
    (http/post (str base-url url-path) options)))

(defn completions
  "https://platform.openai.com/docs/guides/completion"
  [config prompt]
  (let [path "/completions"
        body {:prompt prompt,
              :model "text-davinci-003",
              :max_tokens 100,
              :n 1,
              :stop nil,
              :temperature 0}
        {:keys [status body]} (post-request config path body)]
    (if (= status 200) (get-in body [:choices 0 :text]) body)))

(defn chat-completions
  "https://platform.openai.com/docs/guides/chat"
  [config messages]
  (let [path "/chat/completions"
        body {:model "gpt-3.5-turbo", :messages messages, :temperature 0.7}
        {:keys [status body]} (post-request config path body)]
    (if (= status 200) (get-in body [:choices 0 :message :content]) body)))
