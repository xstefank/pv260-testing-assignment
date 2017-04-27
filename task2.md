# Task 2 - Hotel reservation system

## 2. Attributes, Components and Capabilities
 ### Attributes
  1. Secure - working with personal data / money transactions
  1. Reliable - no random reservation cancellations (when customer arrives, he gets the exact room he reserved)
  1. Available - should be available at any time in case of some problem with reservation after arriving
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
  1. Hotel & property policies
  1. Call center
  1. Refund system
  1. Error logging
  1. Suggestions system
  1. Filtering
  1. Favourites
  1. Top destinations
  1. Subscriptions

### Capabilities
  1. Secure Registration
     * test that valid and unique registration passes
     * test that invalid registration gets adequate error information
     * test that registration is secure and cant be attacked by injection, XSS or XSRF code

  1. Secure Reservation
     * test that correct reservation passes
     * test that outdated reservation fails and the system informs customer about changes
     * test that reservation from unregistered user redirects the user to registration

  1. Secure Cancellation
     * test that cancellation passes until deadline
     * test that cancellation fails after deadline
     * test that passed cancellation for paid reservation gives customer the right to ask for the refund

  1. Secure Refund
     * test that refund request is saved into system
     * test that refund confirm transfers the correct amount to customer
     * test that after refund refusal system notifies customer by email

  1. Secure Payment
     * test that payment info is stored in database
     * test that payment amount is double-checked on server-side
     * test that payment modules are accessible (paypal etc.)

  1. Secure Customer review
     * test that no harm code gets processed from the review form
     * test that valid review passes
     * test that invalid review informs customer about the errors

  1. Reliable Hotel search
     * test that search returns results for existing hotels
     * test that search with no results allows user to search by filter  
     * test that no harm code gets processed from the search form

  1. Display for the appartment - photos / virtual walk
     * test that gallery can be opened
     * test that image slider reacts to swipe events (for mobile devices)
     * test that gallery administration tool can add/remove photos

  1. Hotel image gallery - common areas
     * same as appartment photos

  1. Reliable and transparent reservation for the user
     * test that valid registration proceeds
     * test that invalid registration informs about the errors
     * test that no harm code gets processed or saved to database

  1. Extensible reservation
     * test that reservation can be extended by partial payment
     * test that no more than one extension can be used
     * test that cancellation of extended reservation allows to refund the right amount of money

  1. Transparent refund system
     * test that refund button is shown in the profile after cancellation of the paid reservation
     * test that refund request shows status in profile
     * test that refund response shows status in profile

  1. Stable cancellation
     * test that customer can cancel reservation freely before payment
     * test that cancelled reservation enables other customers to make the same reservation
     * test that cancellation after payment navigates user to refund page

  1. Transparent and credible payment
     * test that payment can be done by one-click functionality for acocunts with payment options filled
     * test that status is shown in profile after payment
     * test that status is shown after payment confirmation

  1. Extensible suggestion system
     * test that suggestion button redirects user to suggestions page
     * test that search with no results shows suggestions according to profile and interests / favourites
     * test that search with no results allows to further filter received suggestions

## 3. Clusters of the biggest risks
  1. Reservation (not adhered from the hotel side, not propagated to the hotel)
      1. Secure reservation
      1. Stable reservation
      1. Transparent reservation
      1. Reliable reservation
      1. Credible reservation
  1. Unability to locate hotels in the area (potential loss of customers)

      1. Reliable hotel search
      1. Extensible search
      1. Extensible filtering
      1. Available search
      1. Stable search - same / similar results for same queries
  1. Internal payment errors (user pays, reservation is successful but the hotel won't receive payment)
      1. Secure payment
      1. Trasnparent payment
      1. Credible payment
      1. Stable payment
      1. Reliable payment
  1. Data loss / theft
      1. Secure payment
      1. Secure registration
      1. Secure data transfer (e.g. to the hotels)
      1. Secure subscription
      1. Secure data storing
  1. Hotel partners (providing sufficient and valid information)
      1. Transparent customer reviews
      1. Detailed hotel information
      1. Responsive photo gallery
      1. Accessible contact information
      1. Our own recommendations
  1. Competition (Other booking systems)
      1. Flight suggestion system
      1. Hotel-Customer communication system
      1. Nearby attractions / destinations (culture) system
      1. 360 view - either hotel / appartment gallery or close range streetview
      1. Weather forecast
