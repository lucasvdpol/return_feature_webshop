describe('Admin pages', () => {

  beforeEach(() => {

    cy.intercept(
      { method: 'POST', url: '/api/auth/login' },
      { "email": "admin@mail.com", "token": "<null>" },
    ).as('login')

    cy.getCategories()


    cy.intercept(
      { method: 'DELETE', url: '/api/product/*' },
      {}
    ).as('deleteProduct')

    cy.intercept(
      { method: 'PUT', url: '/api/product/*' },
      {}
    ).as('deleteProduct')

    cy.getProducts()

    cy.visit('http://localhost:26338/')

    cy.get('[data-testid="login-button"]').should('have.length', 1).click()

    cy.get('input[type="email"]').should('have.length', 1).type('admin@mail.com')

    cy.get('input[type="password"]').should('have.length', 1).type('Password123!')

    cy.get('button[type="submit"]').click();

    cy.wait('@login')

    cy.get('[data-testid="account-button"]').should('have.length', 1).click()

  })

  it('Admin options are visible', () => {

    cy.get('.sidebar > button').should('have.length', 12)

  })

  it('Add product variant', () => {

    cy.get('.sidebar > :nth-child(9)').should('have.length', 1).click()

    cy.getProducts()

    cy.get('select#product').should('have.length', 1).select('1')
    cy.get('input#name').should('have.length', 1).type('New product variant')
    cy.get('input#image').should('have.length', 1).type('1001')
    cy.get('input#color:nth-child(2)').should('have.length', 1).type('#3a2bff')
    cy.get('button[class="submit-btn"]').should('have.length', 1).click()

    cy.location('pathname').should('equal', '/')

  })

  it('Delete product', () => {

    cy.get('.sidebar > :nth-child(10)').should('have.length', 1).click()

    cy.getProducts()

    cy.get('.change-data-container select#product').should('have.length', 1).select('1')
    cy.get('.change-data-container button[class="submit-btn"]').should('have.length', 1).click()

    cy.location('pathname').should('equal', '/')

  })


  it('Edit product', () => {

    cy.get('.sidebar > :nth-child(11)').should('have.length', 1).click()

    cy.getProducts()

    cy.get('select#product').should('have.length', 1).select('1')
    cy.get('form > :nth-child(2) > input').should('have.length', 1).type('!')
    cy.get('button[class="submit-btn"]').should('have.length', 1).click()

    cy.location('pathname').should('equal', '/')

  })

})