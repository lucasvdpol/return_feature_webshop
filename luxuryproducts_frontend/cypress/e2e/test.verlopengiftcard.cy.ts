describe('verlopen giftcard proberen te gebruiken bij een bestelling', () => {
    beforeEach(() => {
        cy.intercept('GET', 'http://localhost:8080/api/product', {
            fixture: 'products.json'
        }).as('getProducts');

        cy.intercept('POST', 'http://localhost:8080/api/auth/login', (req) => {
            const { email, password } = req.body;
            if (email === 'testuser@example.com' && password === 'Welom01!') {
                req.reply({
                    statusCode: 401,
                    body: {
                        message: 'Invalid credentials'
                    }
                });
            } else {
                req.reply({
                    statusCode: 200,
                    body: {
                        token: 'fake-token',
                        user: {
                            email,
                            role: 'admin'
                        }
                    }
                });
            }
        }).as('loginRequest');

        cy.intercept('GET', 'http://localhost:8080/api/giftcard', {
            statusCode: 200,
            body: [{
                id: 1,
                giftcardCode: "LP12345678901234",
                date: "2023-06-01T14:30:00Z",
                endDate: "2025-06-01T14:30:00Z",
                price: 100,
                geschiedenis: "",
                blocked: false,
                user: {
                    id: 1,
                    email: "testuser@example.com",
                    password: "Welkom01!"
                }
            }]
        }).as('getGiftcardMe');


    })

    it('moet een melding geven voor de verlopen giftcard', () => {
        cy.visit('http://localhost:26338');

        cy.get('[data-testid="login-button"]').should('be.visible').click();

        cy.get('input[type="email"]').type("admin@gmail.com");
        cy.get('input[type="password"]').type("welkom01!");
        cy.get('button[type="submit"]').click();

        cy.visit('http://localhost:26338/checkout');

        cy.window().then((win) => {
            const w = win as any;
            w.winkelmandService.addToCart('test Product', 1, 7999, "1");
        });

        cy.contains('Kies een giftcard').click();

        cy.contains('Er zijn 1 verlopen giftcards die niet gebruikt kunnen worden:').should('be.visible');

        cy.contains('Code: LP12345678901234 - Waarde: €100').should('be.visible');
    })

})