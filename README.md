# clj-openai

A simple ChatGPT client for Clojure.

## Installation

[![Clojars Project](https://img.shields.io/clojars/v/com.flexiana/clj-openai.svg)](https://clojars.org/com.flexiana/clj-openai)

## Usage

```clojure
(require '[clj-openai.core :as ai])
```

`clj-openai` provides 2 functions: `completions` and `chat-completions`. They both take config map as first argument. They both return string on success and error map on failure.

### [completions](https://platform.openai.com/docs/guides/completion)

Function parameters:
1. config map
2. prompt string

Example usage:

```clojure
(ai/completions {:api-key api-key, :base-url base-url}
                "hello")
```

### [chat-completions](https://platform.openai.com/docs/guides/chat)

Function parameters:
1. config map
2. vector of message objects

Example usage:

```clojure
(ai/chat-completions {:api-key api-key, :base-url base-url}
                     [{:role "user", :content "hello"}])
```

### Config map

Config map is passed as first argument to all provided functions.
Available config keys:

- :api-key - mandatory, managed [here](https://platform.openai.com/account/api-keys)
- :base-url - optional, defaults to `https://api.openai.com/v1` 

### Errors

Example:

```clojure
{:error
 {:message "Incorrect API key provided."
  :type "invalid_request_error"
  :param nil
  :code "invalid_api_key"}}
```

## Development

Start nREPL:

    $ ./bin/nrepl

Run the project's tests (accepts all -X arguments, [docs](https://github.com/cognitect-labs/test-runner)):

    $ ./bin/test

Run tests in single namespace:

    $ ./bin/test-ns clj-openai.core-test

Run a single test:

    $ ./bin/test-var clj-openai.core-test/completion


Run the project's CI pipeline and build a jar:

    $ ./bin/ci

Install the jar locally:

    $ clj -T:build local-install

Deploy the jar to clojars ('CLOJARS_USERNAME' and 'CLOJARS_PASSWORD' env vars must be set):

    $ ./bin/deploy

## License

Copyright © 2023 Staifa

_EPLv1.0 is just the default for projects generated by `deps-new`: you are not_
_required to open source this project, nor are you required to use EPLv1.0!_
_Feel free to remove or change the `LICENSE` file and remove or update this_
_section of the `README.md` file!_

Distributed under the Eclipse Public License version 1.0.