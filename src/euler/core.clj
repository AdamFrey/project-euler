(ns euler.core
  (:require [clojure.data.int-map :as i]
            [criterium.core :as c]))

;; Problem 4

;; A palindromic number reads the same both ways. The largest palindrome made
;; from the product of two 2-digit numbers is 9009 = 91 × 99.
;; Find the largest palindrome made from the product of two 3-digit numbers.

(defn palindrome? [n]
  (let [string  (str n)
        reverse (clojure.string/reverse string)]
    (not (some false? (map = string reverse)))))

(defn largest-palindrome-product
  "Returns the largest palindome product of two three digit numbers"
  []
  (loop [x   100
         max 0]
    (if (<= 1000 x)
      max
      (if-let [y (first  ;; Someday we could use "seek" here? http://dev.clojure.org/jira/browse/CLJ-2056
                   (filter #(palindrome? (* x %)) (reverse (range x 1000))))]
        (let [product (* x y)]
          (if (< max product)
            (recur (inc x) (* x y))
            (recur (inc x) max)))
        ;; no palindromes
        (recur (inc x) max)))))

(comment
  (largest-palindrome-product))

;; Problem 14
;; The following iterative sequence is defined for the set of positive integers:

;; n → n/2 (n is even)
;; n → 3n + 1 (n is odd)

;; Using the rule above and starting with 13, we generate the following sequence:

;; 13 → 40 → 20 → 10 → 5 → 16 → 8 → 4 → 2 → 1
;; It can be seen that this sequence (starting at 13 and finishing at 1) contains 10 terms. Although it has not been proved yet (Collatz Problem), it is thought that all starting numbers finish at 1.

;; Which starting number, under one million, produces the longest chain?

;; NOTE: Once the chain starts the terms are allowed to go above one million.

(defn collatz-sequence-cache
  "Given a number and and a collatz chain length cache as a map, returns a new
  cache with the number keyed to it's collatz sequence length, as well as each
  other number in its sequence. Numbers present in the supplied cache will not
  be re-calculated."
  [n cache]
  (if (= n 1)
    ;; Terminate at 1
    (assoc cache n 1)
    ;; Calculate each link in chain
    (let [next-number            (if (even? n)
                                   (/ n 2)
                                   (+ 1 (* 3 n)))
          cache                  (if (contains? cache next-number)
                                   cache
                                   (collatz-sequence-cache next-number cache))
          remaining-chain-length (get cache next-number)]
      (assoc cache n (inc remaining-chain-length)))))

(defn longest-collatz-sequence []
  (->> (range 1 1000000)
    (reduce (fn [cache int]
              (collatz-sequence-cache int cache)) {})
    (sort-by second >)
    (first)))

(comment
  (-> (c/bench (longest-collatz-sequence) :verbose)
    (c/with-progress-reporting)))
