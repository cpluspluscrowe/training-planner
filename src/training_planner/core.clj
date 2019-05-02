(ns training-planner.core
  (:use clojure.test)
  (:require [clojure.string :as str])
  (:gen-class))

                                        ; shoot for M: 80, T: 130, W: 50 Th: 100: F: 0, S: 300, S: 50
; Fill all leftovers with ER, pad with 1/2 warmup and 1/2 cooldown at intensity 2

(defstruct workout :type :duration)

(def intensities (hash-map
                  :RI 10
                  :TR 8
                  :SSR 7
                  :ER 5
                  :RR 4))

(def set-duration-min (hash-map
                       :RI 1
                       :TR 8
                       :SSR 20
                       :ER 1000
                       :RR 20))

(def set-duration-max (hash-map
                       :RI 3
                       :TR 20
                       :SSR 60
                       :ER 1000
                       :RR 60))

(def total-duration-min (hash-map
                         :RI 12
                         :TR 30
                         :SSR 30
                         :ER 1000
                         :RR 1000))

(def total-duration-max (hash-map
                         :RI 24
                         :TR 60
                         :SSR 120
                         :ER 1000
                         :RR 1000))

(def intensity-tss (hash-map
                    1 20/60
                    2 30/60
                    3 40/60
                    4 50/60
                    5 60/60
                    6 70/60
                    7 80/60
                    8 100/60
                    9 120/60
                    10 140/60))

; also the number of sets of that duration


(defn create-intervals
  ([type duration]
   (let [total-min (type total-duration-min)
         total-max (type total-duration-max)
         max-intervals (int (/ total-max duration))
         min-intervals (int (/ total-min duration))]
     (create-intervals min-intervals max-intervals (list))))
  ([min-intervals max-intervals intervals]
   (cond (< min-intervals (+ max-intervals 1))
         (create-intervals (+ min-intervals 1) max-intervals (conj intervals min-intervals))
         :else intervals)))

(defn create-duration-range
  ([type-of-workout]
   (let [min-duration (max 1 (type-of-workout set-duration-min))
         max-duration (type-of-workout set-duration-max)]
     (create-duration-range min-duration max-duration (list))))
  ([min-duration max-duration durations]
   (cond (< min-duration (+ max-duration 1))
         (create-duration-range (+ min-duration 1) max-duration (conj durations  min-duration))
         :else durations)))

(defstruct session :workouts)

(defn tempo-workout
  ([duration count] (tempo-workout duration count (list (struct workout :RR 5)))) ; add 5 minute warmup to the start
  ([duration count workouts]
   (cond (> count 0)
         (tempo-workout duration (- count 1) (conj workouts (struct workout :RI duration)))
         :else
         (struct session (conj workouts (struct workout :RR 5)))))) ; add 5 minute cooldown at the end

(defn get-workout-tss [workout]
  (let [rpe (intensities (:type workout))]
    (int (* (get intensity-tss rpe) (:duration workout)))))

(defn get-workouts-tss [workouts]
  (cond (:workouts workouts) (get-workouts-tss (:workouts workouts))
        (empty? workouts) 0
        :else
           (+ (get-workout-tss (first workouts)) (get-workouts-tss (rest workouts)))))

(defn get-workouts [session]
  (get session :workouts))

(defn map-session-workouts-to-tss [sessions]
  (map get-workouts sessions))

(defn have-tss-for-workout? [goal-tss workout]
  (> (get-workout-tss workout) goal-tss))

(defn TSS [run-type-durations]
  (let [running-interval (:RI run-type-durations)
        tempo (:TR run-type-durations)
        steady-state (:SSR run-type-durations)
        endurance (:ER run-type-durations)
        recovery (:RR run-type-durations)]))

(defn generate-intervals-at-duration [duration intervals]
  (map (partial tempo-workout duration) intervals))

(defn create-possible-workouts
  ([type]
   (let [durations (create-duration-range type)]
     (create-possible-workouts type durations (list))))
  ([type durations combinations]
   (if (empty? durations) (flatten combinations)
       (let [duration (first durations)
             intervals (create-intervals type duration)]
         (create-possible-workouts type (rest durations)
                                   (conj (generate-intervals-at-duration duration intervals) combinations))))))

(defn ngt [goal-tss workout-tss]
  (not (< goal-tss workout-tss)))

(defn get-workouts-for-tss [type tss]
  (let [sessions (create-possible-workouts type)
        workouts-tss (map get-workouts-tss sessions)]
    (filter (partial ngt tss) workouts-tss)))

(defn -main
  "Creates a training plan"
  [& args]
  (println (get-workouts-for-tss :RI 50))
  (println "Go run!!"))
