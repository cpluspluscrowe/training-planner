(ns training-planner.core-test
  (:require [clojure.test :refer :all]
            [training-planner.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 0))))


(deftest get-workout-tss-test
  (let [workout (struct workout :RI 3)]
    (is (get-workout-tss workout) 3)
   ))
