# phi-accrual-failure-detector
A port of [Akka's Phi Accrual Failure Detector](https://github.com/akka/akka/blob/master/akka-remote/src/main/scala/akka/remote/PhiAccrualFailureDetector.scala).

Implementation of 'The Phi Accrual Failure Detector' by Hayashibara et al. as defined in [their paper](http://ddg.jaist.ac.jp/pub/HDY+04.pdf)

The suspicion level of failure is given by a value called φ (phi). The basic idea of the φ failure detector is to express the value of φ on a scale that is dynamically adjusted to reflect current network conditions. A configurable threshold is used to decide if φ is considered to be a failure.

The value of φ is calculated as:

    φ = -log10(1 - F(timeSinceLastHeartbeat)

where F is the cumulative distribution function of a normal distribution with mean and standard deviation estimated from historical heartbeat inter-arrival times.

## Install

### Gradle

    dependencies {
        compile 'org.komamitsu:phi-accrual-failure-detector:1.0.0'
    }

### Maven

    <dependency>
        <groupId>org.komamitsu</groupId>
        <artifactId>phi-accrual-failure-detector</artifactId>
        <version>1.0.0</version>
    </dependency>
 
 
## Usage
 
    PhiAccrualFailureDetector failureDetector = new PhiAccrualFailureDetector.Builder().build();
    System.out.println("The node is alive.");
    for (int i = 0; i < 5; i++) {
        failureDetector.heartbeat();
        System.out.println(failureDetector.phi());
        TimeUnit.SECONDS.sleep(1);
    }
    System.out.println("The node got crashed.");
    for (int i = 0; i < 5; i++) {
        System.out.println(failureDetector.phi());
        TimeUnit.SECONDS.sleep(1);
    }
    System.out.println("The node is up.");
    for (int i = 0; i < 5; i++) {
        failureDetector.heartbeat();
        System.out.println(failureDetector.phi());
        TimeUnit.SECONDS.sleep(1);
    }

    >>>
    The node is alive.
    0.07503303214093988
    0.04123372001754947
    0.02973367253880404
    0.024211164763365475
    0.0210350451959075
    The node got crashed.
    0.434230547557814
    2.026327722139659
    5.53345508216027
    12.239567237632542
    23.67083400864969
    The node is up.
    0.09372488186862751
    0.09191309083825062
    0.08979724362880628
    0.0875398030863105
    0.08525179892208078
