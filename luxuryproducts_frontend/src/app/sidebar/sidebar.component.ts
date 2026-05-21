import {Component, inject, signal,} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {ProductinfoService} from '../services/productinfo.service';
import {TranslatePipe} from '@ngx-translate/core';
import {CategoryService} from "../services/category.service";
import {Category} from "../models/Category.model";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  imports: [
    FormsModule,
    TranslatePipe
  ],
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {
  private categoryService = inject(CategoryService);
  private productService = inject(ProductinfoService);
  categories = this.categoryService.getCategories();
  public categoryList= signal([''])
  searchQuery: string = '';

  onSearch() {
    this.productService.filterProductsBySearch(this.searchQuery.toLowerCase());
  }

  onCategory(categoryname: string) {
    if (categoryname === 'All') {
      this.categoryList.set(["All"]);
    } else {
      const allIndex = this.categoryList().indexOf('All');
      if (allIndex > -1) {
        this.categoryList().splice(allIndex, 1);
      }

      const index = this.categoryList().indexOf(categoryname);
      if (index > -1) {
        this.categoryList().splice(index, 1);
      } else {
        this.categoryList().push(categoryname);
      }
    }
    this.productService.filterByCategory(this.categoryList());
  }

  get isAllSelected(): boolean {
    return this.categoryList().length == 1;
  }






}

