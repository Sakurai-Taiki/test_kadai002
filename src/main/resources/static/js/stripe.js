const stripe = Stripe('sk_test_51QgcYm7vXAaoTR5xdUuDEOgxsA58QN9vDaE2c50l62Tfj5aXYTeJ25AryNj3IYAV4uNdwFUq40ro6m0snlPJ1ktY00rDScG4ZR');
 const paymentButton = document.querySelector('#paymentButton');
 
 paymentButton.addEventListener('click', () => {
   stripe.redirectToCheckout({
     sessionId: sessionId
   })
 });