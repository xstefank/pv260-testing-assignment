# Task 2 - Hotel reservation system

## 2. Attributes, Components and Capabilities
 ### Attributes
  1. Secure - working with personal data / money transactions
  1. Reliable - no random reservation cancellations (when customer arrives, he gets the exact room he reserved)
  1. Available - should be available at any time in case of some problem with reservation after arriving ???
  1. Usablity / Transparency - simple reservation which is transparent for the user
  1. Credibility - payments reach end-point hotels 
  1. Stability - any failure of the system cannot influence booked reservations
  1. Extensibility - adding new hotels, new features
### Components
  1. Registration and Login
  1. Payment
  1. Reservation
  1. Cancellation & cancellation policy
  1. Search
  1. Hotel rating - Customer reviews
  1. Internal administration ???
  1. Call center
  1. Refund system
  1. Error logging
  1. Suggestions system
  1. Filtering
  1. Favourites
  1. ComponentN
  1. ComponentN
### Capabilities
  1. Secure Registration
     * test that valid and unique registration passes
     * test that invalid registration gets adequate error information
     * test that registration is secure and cant be attacked by injection, XSS or XSRF code
  1. Secure Reservation
     * test that correct reservation passes
     * test that outdated reservation fails and informs customer about changes
     * test that reservation from unregistered user redirects him to registration ??? neccessary?

  1. Secure Cancellation
     * test that cancellation passes until deadline
     * test that cancellation fails after deadline
     * test that passed cancellation for paid reservation gives customer the right to ask for refundation 

  1. Secure Refundation
     * test that refund request is saved into system
     * test that refund confirm transfers the correct amount to customer
     * test that refund refuse contacts customer by email ???

  1. Secure Payment
     * test that payment info is stored in database
     * testN
     * testN

  1. Secure??? Customer review
     * test that no harm code gets processed from the review form
     * test that valid review passes
     * test that invalid review informs customer about the errors

  1. Reliable Hotel search
     * test that search returns results for existing 
     * test that search with no results allows to search by filter  
     * test that no harm code gets processed from the search form
  1. Appartment view ???
     * testN
     * testN
     * testN
  1. Appartment image gallery
     * testN
     * testN
     * testN

  1. Reliable and credible/transparent reservation for the user
     * testN
     * testN
     * testN

  1. Extensible reservation
     * testN
     * testN
     * testN

  1. Transparent refund system
     * testN
     * testN
     * testN

  1. Stable cancellation
     * testN
     * testN
     * testN

  1. Transparent and credible payment
     * testN
     * testN
     * testN

  1. Extensible suggestion system
     * testN
     * testN
     * testN
## 3. Clusters of the biggest risks
  1. Reservation not adhered from the hotel side
  1. Reservation not propagated to the hotel
  1. Unability to locate hotels in location - potential loss of customers
  1. Internal payment errors - user pays, reservation successful but hotel won't receive payment
  1. ClusterN...
  1. ClusterN...

