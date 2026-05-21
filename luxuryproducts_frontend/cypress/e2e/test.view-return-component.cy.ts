describe("Check review return requests", () => {
    beforeEach(() => {
        cy.getProducts()
        cy.visit('/');
        cy.get('[data-testid="login-button"]').click();
        cy.get('input[type="email"]').type('admin@gmail.com');
        cy.get('input[type="password"]').type('Welkom01!');
        cy.get('button[type="submit"]').click();
        cy.loginAsAdmin()
        cy.get('.login-popup').should('not.exist');
        cy.get('[data-testid="account-button"]').should('be.visible');
        cy.visit('/account');
        cy.getAllReturnRequests()
        cy.get('[data-testid="allreturns-button"]').click({ force: true });
        cy.get('.request').should('exist').and('be.visible');
        cy.get('[data-testid="acceptbutton"]').should('exist');
    });
    it('Should be able to decline return request', () => {
        cy.declineReturnRequest()
        cy.getNoReturnRequests()
        cy.get('[data-testid="declinebutton"]').should('exist').click();
        cy.get('.request').should('not.exist')

    });
    it('Should be able to accept request', () => {
        cy.acceptReturnRequest()
        cy.getNoReturnRequests()
        cy.get('[data-testid="acceptbutton"]').should('exist').click();
        cy.get('.request').should('not.exist')
    })
})

