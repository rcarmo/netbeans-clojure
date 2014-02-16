; <h3>From <a href="http://en.wikibooks.org/wiki/Clojure_Programming/Examples/Lazy_Fibonacci">
; Clojure Programming/Examples/Lazy Fibonacci</a></h3>
;
; <p>We can use <b>iterate</b> to generate pairs of [a b]
; and then take the first of each one:</p>

(defn fib-step [[a b]]
  [b (+ a b)])

(defn fib-seq []
  (map first (iterate fib-step [0 1])))

user> (take 20 (fib-seq))
(0 1 1 2 3 5 8 13 21 34 55 89 144 233 377 610 987 1597 2584 4181)
