
describe('Giftcard kopen integratietest met mockdata', () => {
    const email = 'testuser@example.com';
    const password = 'Welkom01!';
    const expectedGiftcardCode = 'LP12345678901234';

    const giftcardMock = {
        description: 'Giftcard van €100',
        price: 100,
        quantity: 1,
        img: '12'
    };

    const orderMock = {
        id: 1,
        orderDate: '2025-06-01',
        customerName: email,
        winkelwagen: [
            {
                productName: 'Giftcard van €100',
                quantity: 1,
                price: 100,
                giftcardCode: [expectedGiftcardCode]
            }
        ],
        totalPrice: 100
    };


    beforeEach(() => {
        cy.intercept('GET', 'http://localhost:8080/api/product', {
            fixture: 'products.json'
        }).as('getProducts');

        cy.intercept('GET', '/giftcard', {
            statusCode: 200,
            body: [{
                giftcardCode: "LP12345678901234",
                price: 100,
                blocked: false,
                geschiedenis: '',
                date: '2025-06-01T14:30:00Z',
                endDate: '2027-06-01T14:30:00Z',
                id: 1
            }]
        }).as('accountGiftcardsWithCodes');


        cy.intercept('POST', '/api/giftcard', (req) => {

            req.reply({
                statusCode: 200,
                body: {
                    giftcardCode: "LP12345678901234",
                    price: 100,
                    id: 1,
                    blocked: false,
                    geschiedenis: '',
                    date: '2025-06-01T14:30:00Z',
                    endDate: '2027-06-01T14:30:00Z'
                }
            });
        }).as('createGiftcard');


        cy.intercept('GET', '/api/giftcardEntity', {
            statusCode: 200,
            body: [giftcardMock]
        }).as('giftcardsRequest');

        cy.intercept('GET', '/api/orderEntity/*', {
            statusCode: 200,
            body: [orderMock]
        }).as('accountGiftcards');

        cy.intercept('POST', '/api/orderEntity', {
            statusCode: 200,
            body: {giftcardCode: "LP12345678901234"}
        }).as('placeOrder');

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

    });

    it('kan giftcard kopen en ziet mockcode na aankoop', () => {
        cy.visit('/');
        cy.contains('Giftcards').click();
        cy.wait('@giftcardsRequest');

        cy.contains('Giftcard van €100')
        cy.contains('Toevoegen').click();

        cy.get('[data-testid="login-button"]').contains('Login').click();

        cy.get('input[type="email"]').type(email);
        cy.get('input[type="password"]').type(password);
        cy.get('button[type="submit"]').click();

        cy.get('.error').should('not.exist');


        cy.get('.cart-icon').click();
        cy.contains('Afrekenen').click();
        cy.url().should('include', '/checkout');

        cy.get('form input').each(($input) => {
            cy.wrap($input).type('a');
        });
        cy.contains('Plaats bestelling').click();
        cy.wait('@placeOrder');

        cy.wait('@createGiftcard');


        cy.window().then((win) => {
            const w = win as any;
            w.checkoutService.giftcardlist = [
                {
                    orderId: 1,
                    giftcards: [
                        { productName: 'Giftcard van €100', code: "LP12345678901234" }
                    ]
                }
            ];
        });

        cy.wait(1000)
        cy.get('[data-testid="vieworder-button"]').click();

        cy.contains("LP12345678901234").should('be.visible');


    });
});
