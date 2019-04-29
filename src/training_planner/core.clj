(ns training-planner.core
  (:gen-class))

                                        ; shoot for M: 80, T: 130, W: 50 Th: 100: F: 0, S: 300, S: 50
; Fill all leftovers with ER, pad with 1/2 warmup and 1/2 cooldown at intensity 2

(defstruct workout :type :duration)

(defn get-workout-tss [workout]
  (let [intensity (workout.type intensities)]
    (* (intensity intensity-tss) workout.duration)
    ))

(def intensities (hash-map
                   :RI 10
                   :TR 8.5
                   :SSR 7
                   :ER 5.5
                   :RR 4.5
                   ))

(def set-duration-min (hash-map
                  :RI 1
                  :TR 8
                  :SSR 20
                  :ER 1000
                  :RR 20
                  ))

(def set-duration-max (hash-map
                   :RI 3
                   :TR 20
                   :SSR 60
                   :ER 1000
                   :RR 60
                   ))

(def total-duration-min (hash-map
                   :RI 12
                   :TR 30
                   :SSR 30
                   :ER 1000
                   :RR 1000
                   ))

(def total-duration-max (hash-map
                     :RI 24
                     :TR 60
                     :SSR 120
                     :ER 1000
                     :RR 1000
                     ))

(def intensity-tss (hash-map
                     1 20
                     2 30
                     3 40
                     4 50
                     5 60
                     6 70
                     7 80
                     8 100
                     9 120
                     10 140
                     ))

(defn TSS [run-type-durations]
  (let [running-interval (:RI run-type-durations)
        tempo (:TR run-type-durations)
        steady-state (:SSR run-type-durations)
        endurance (:ER run-type-durations)
        recovery (:RR run-type-durations)
        ]
  (println running-interval)
  ))

(defn -main
  "Creates a training plan"
  [& args]
  (TSS (hash-map :RI 1 :TR 2 :SSR 3 :ER 4 :RR 5))
  (println "Go run!!"))
