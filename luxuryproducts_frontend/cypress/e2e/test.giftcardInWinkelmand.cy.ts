describe('Giftcard Integration Test', () => {

    beforeEach(() => {
        cy.intercept('GET', 'http://localhost:8080/api/product', {
            fixture: 'products.json'
        }).as('getProducts');

        cy.intercept('POST', 'http://localhost:8080/api/auth/login', (req) => {
            const { email, password } = req.body;
            if (email === 'admin@gmail.com' && password === 'Welom01!') {
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
        cy.fixture('user').then((user) => {
            cy.visit('http://localhost:26338/');
            cy.get('[data-testid="login-button"]').should('be.visible').click();

            cy.get('input[type="email"]').type(user.email);
            cy.get('input[type="password"]').type(user.password);
            cy.get('button[type="submit"]').click();

            cy.get('.error').should('not.exist');

        });

        cy.intercept('GET', 'http://localhost:8080/api/giftcardEntity', {
            fixture: 'giftcards.json'
        }).as('getGiftcards');

        cy.visit('http://localhost:26338/');

        cy.get('[data-testid="giftcard-button"]').contains('Giftcards').click();

        cy.wait('@getGiftcards');

        cy.get('.product-container .product-card').first().within(() => {
            cy.get('select[name="aantal"]').select('2');
            cy.get('button').contains(/toevoegen/i).click();
        });

        cy.get('.cart-container').trigger('mouseenter');

        cy.get('.cart-badge').should(($badge) => {
            const text = $badge.text();
            const [productCount, giftcardCount] = text.split(' ').map(Number);
            expect(giftcardCount).to.be.greaterThan(0);
        });
    });

    it('should navigate to giftcards, add one to the cart, and proceed to checkout', () => {

        cy.get('app-winkelwagen').within(() => {
            cy.contains('button', 'Afrekenen').click();
        });

        cy.url().should('include', '/checkout');
    });

    it('Should delete giftcard from cart', () => {

        cy.get('app-winkelwagen').within(() => {
            cy.get('.delete-btn').first().click();
        });

        cy.get('app-winkelwagen').within(() => {
            cy.contains('.item-name', 'Giftcard 50').should('not.exist');
        });
    })

    it('Should add one more giftcard to the cart using +', () => {
        let initialQuantity: number;

        cy.get('app-winkelwagen').within(() => {
            cy.get('.cart-item').first().find('.quantity-controls span').eq(1).invoke('text').then((text) => {
                initialQuantity = parseInt(text.trim(), 10);

                cy.get('.cart-item').first().find('button').contains('+').click();

                cy.get('.cart-item').first().find('.quantity-controls span').eq(1).should(($span) => {
                    const newQuantity = parseInt($span.text().trim(), 10);
                    expect(newQuantity).to.equal(initialQuantity + 1);
                });
            });
        });
    });

    it('Should add one less giftcard to the cart using -', () => {
        let initialQuantity: number;

        cy.get('app-winkelwagen').within(() => {
            cy.get('.cart-item').first().find('.quantity-controls span').eq(1).invoke('text').then((text) => {
                initialQuantity = parseInt(text.trim(), 10);

                cy.get('.cart-item').first().find('button').contains('-').click();

                cy.get('.cart-item').first().find('.quantity-controls span').eq(1).should(($span) => {
                    const newQuantity = parseInt($span.text().trim(), 10);
                    expect(newQuantity).to.equal(initialQuantity - 1);
                });
            });
        });
    });

    it('Should giftcard afrekeken make giftcard', () => {
        cy.get('app-winkelwagen').within(() => {
            cy.contains('button', 'Afrekenen').click();
        });
        cy.url().should('include', '/checkout');
        cy.get('form input').each(($input) => {
            cy.wrap($input).type('a');
        });
        cy.get('button[type="submit"]').click();
        cy.intercept('POST', 'http://localhost:8080/api/giftcard', (req) => {
            expect(req.body).to.have.property('date');
            expect(req.body).to.have.property('endDate');
            expect(req.body).to.have.property('price').and.to.be.greaterThan(0);
            expect(req.body).to.not.have.property('giftcardCode');

            req.reply({
                statusCode: 201,
                body: {
                    ...req.body,
                    id: 123,
                    giftcardCode: 'RANDOM-CODE-1234',
                },
            });
        }).as('createGiftcard');


    })


});
