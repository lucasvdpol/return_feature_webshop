declare namespace Cypress {
    interface Chainable {
        login(): Chainable<void>;
        getProducts(): Chainable<void>;
        getCategories(): Chainable<void>;
        getOrders(): Chainable<void>;
        getReturnRequests(): Chainable<void>;
        getAllReturnRequests(): Chainable<void>;
        loginAsAdmin(): Chainable<void>;
        acceptReturnRequest(): Chainable<void>;
        declineReturnRequest(): Chainable<void>
        getNoReturnRequests(): Chainable<void>
    }
}


Cypress.Commands.add('login', () => {
    cy.intercept('POST', '/api/auth/login', (req) => {
        req.reply({
            statusCode: 200,
            body: {
                email: "admin@gmail.com",
                token: "faketoken"
            },
        });
    }).as('postLogin');
})

Cypress.Commands.add('getProducts', () => {
    cy.fixture('products').then((products) => {
        cy.intercept('GET', '/api/product', {
            statusCode: 200,
            body: products
        }).as('getProducts');
    });
})

Cypress.Commands.add('getCategories', () => {
    cy.fixture('categories').then((categories) => {
        cy.intercept('GET', '/api/categories', {
            statusCode: 200,
            body: categories
        }).as('getCategories');
    });
})


Cypress.Commands.add('getOrders', () => {
    cy.fixture('orders').then((orders) => {
        cy.intercept('GET', '/api/orderEntity/*', {
            statusCode: 200,
            body: orders
        }).as('getOrders');
    });
})

Cypress.Commands.add('getReturnRequests', () => {
    cy.fixture('returnrequests').then((returnRequests) => {
        cy.intercept('GET', '/api/return/user', {
            statusCode: 200,
            body: returnRequests
        }).as('getUserReturnRequests');
    });
})

Cypress.Commands.add('getAllReturnRequests', () => {
    cy.fixture('returnrequests').then((returnRequests) => {
        cy.intercept('GET', '/api/return', {
            statusCode: 200,
            body: returnRequests
        }).as('getAllReturnRequests');
    });
})

Cypress.Commands.add('loginAsAdmin', () => {
    const header = btoa(JSON.stringify({ alg: "HS256", typ: "JWT" }));
    const payload = btoa(JSON.stringify({
        email: "admin@gmail.com",
        role: "ADMIN",
        exp: Math.floor(Date.now() / 1000) + 3600
    }));
    const fakeToken = `${header}.${payload}.signature`;

    cy.intercept('POST', '/api/auth/login', {
        statusCode: 200,
        body: {
            email: 'admin@gmail.com',
            token: fakeToken,
            role: 'ADMIN'
        }
    }).as('postLogin');
});

Cypress.Commands.add('acceptReturnRequest', () => {
    cy.intercept('POST', '/api/return/accept/*', {
        statusCode: 200,
        body: 'Accepted'
    }).as('acceptReturn');
})

Cypress.Commands.add('declineReturnRequest', () => {
    cy.intercept('POST', '/api/return/decline/*', {
        statusCode: 200,
        body: 'Declined'
    }).as('declineReturn');
})

Cypress.Commands.add('getNoReturnRequests', () => {
    cy.fixture('noreturnrequests').then((returnRequests) => {
        cy.intercept('GET', '/api/return', {
            statusCode: 200,
            body: returnRequests
        }).as('getNoReturnRequests');
    });
})




