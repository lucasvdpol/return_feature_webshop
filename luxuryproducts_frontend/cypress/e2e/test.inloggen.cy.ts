describe('test', () => {
    it('should show error for invalid email input', function () {
        cy.intercept('GET', 'http://localhost:8080/api/product', {
            fixture: 'products.json'
        }).as('getProducts');

        cy.fixture('user').then((user) => {
            cy.intercept('POST', 'http://localhost:8080/api/auth/login', (req) => {
                const { email, password } = req.body;

                if (email !== user.email && password === user.password) {
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

            cy.visit('http://localhost:26338/');
            cy.get('[data-testid="login-button"]').contains('Login').click();
            cy.get('input[type="email"]').type('invalidemail');
            cy.get('input[type="password"]').type(user.password);
            cy.get('button[type="submit"]').click();
            cy.get('.error')
                .should('be.visible')
                .and('not.have.text', '');
            cy.get('input[type="email"]').then(($input) => {
                const inputEl = $input[0] as HTMLInputElement;
                expect(inputEl.validity.valid).to.be.false;
            });
        });
    });

    it('should show error for invalid password input', function () {
        cy.fixture('user').then((user) => {
            cy.intercept('POST', 'http://localhost:8080/api/auth/login', (req) => {
                const { email, password } = req.body;

                if (email === user.email && password !== user.password) {
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
            cy.visit('http://localhost:26338/');
            cy.get('[data-testid="login-button"]').contains('Login').click();
            cy.get('input[type="email"]').type(user.email);
            cy.get('input[type="password"]').type('foutwachtwoord');
            cy.get('button[type="submit"]').click();
            cy.get('.error')
                .should('be.visible')
                .and('not.have.text', '');
        });
    });


    it('should login successfully with valid user credentials', function () {
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
            cy.get('[data-testid="login-button"]').contains('Login').click();

            cy.get('input[type="email"]').type(user.email);
            cy.get('input[type="password"]').type(user.password);
            cy.get('button[type="submit"]').click();

            cy.get('.error').should('not.exist');

        });
    });

});
