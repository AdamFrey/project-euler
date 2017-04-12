(ns romr.euler-test
  (:require [clojure.test :refer :all]
            [euler.core :as euler]))

(deftest problem-4
  (testing "Largest palindrome product"
    (is (= (euler/largest-palindrome-product) 906609))))

(deftest problem-14
  (testing "Longest Collatz sequence"
    (let [[starting-number seq-length] (euler/longest-collatz-sequence)]
      (is (= starting-number 837799))
      (is (= seq-length 525)))))
