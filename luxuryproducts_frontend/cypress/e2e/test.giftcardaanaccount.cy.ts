describe('Giftcard koppelen aan account', () => {
    beforeEach(() => {
        cy.intercept('GET', 'http://localhost:8080/api/product', {
            fixture: 'products.json'
        }).as('getProducts');

        cy.visit('http://localhost:26338');
    });

    it('moet giftcard koppelen aan account en tonen', () => {
        cy.visit("http://localhost:26338/accountgiftcards")
        cy.intercept('PUT', 'http://localhost:8080/api/giftcard/emailUser/LP12345678901234', {
            statusCode: 200,
            body: {
                message: 'Updated emailgiftcard with code'
            }
        }).as('updateEmail');

        cy.get('#code').type('LP12345678901234');
        cy.get('#email').type('testuser@example.com');

        cy.contains('Update e-mail').click();




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

        cy.visit('http://localhost:26338');

        cy.get('[data-testid="login-button"]').should('be.visible').click();

        cy.get('input[type="email"]').type("testuser@example.com");
        cy.get('input[type="password"]').type("welkom01!");
        cy.get('button[type="submit"]').click();

        cy.visit('http://localhost:26338/accountgiftcards');

        cy.intercept('GET', '/api/giftcard', {
            statusCode: 200,
            body: [
                {
                    id: 1,
                    giftcardCode: "LP12345678901234",
                    date: '2025-06-01T14:30:00Z',
                    endDate: '2027-06-01T14:30:00Z',
                    blocked: false,
                    geschiedenis: 'Toegevoegd aan account',
                    price: 100,
                    user: {
                        id: 1,
                        email: 'testuser@example.com',
                        password: 'Welkom01!'
                    }
                }
            ]
        }).as("getGiftcards");

        cy.get('.zienmijngiftcards').click()



        cy.wait('@loginRequest');

        cy.contains('LP12345678901234').should('be.visible');
        cy.contains('testuser@example.com').should('be.visible');

    });
});
