const stripe = Stripe('pk_test_51Oi4GuA31AgWmSsAWd2TZvfJLHQRY25hpmIFHXwfXoBiFdEOtTmlUTVWRwZiBT2gJqtsnVjTmoULd1zF2BbkOd8d00ntK4LYgl');
const paymentButton = document.querySelector('#paymentButton');

paymentButton.addEventListener('click', () => {
	stripe.redirectToCheckout({
		sessionId: sessionId
	})
});

// ↓分からず断念↓
/*
stripe.createPaymentMethod('card', card).then(function(result) {
	if (result.error) {
		// エラー処理
		
	} else {
		// 成功した場合、バックエンドにPaymentMethod IDを送信
		console.log('PaymentMethod ID:', result.paymentMethod.id);
		fetch('', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({ paymentMethodId : result.paymentMethod.id }),
		})
		.then(function(response) {
			// サーバーからの応答を処理
		})
		.catch(function(error) {
			console.error('Error', error);
		});
	}
});
*/