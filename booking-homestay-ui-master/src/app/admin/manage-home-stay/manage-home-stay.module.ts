import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ManageHomeStayComponent} from './manage-home-stay.component';
import {ManageHomeStayRoutingModule} from './manage-home-stay-routing.module';
import {HomestayComponent} from './home-stay/homestay.component';
import {
  NbAccordionModule,
  NbAutocompleteModule,
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbIconModule,
  NbInputModule,
  NbPopoverModule,
  NbSelectModule,
  NbSpinnerModule,
  NbTabsetModule,
  NbTooltipModule,
  NbTreeGridModule,
} from '@nebular/theme';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AddressComponent} from './address/address.component';
import {DetailHomestayComponent} from './home-stay/detail-homestay/detail-homestay.component';
import {PlaceComponent} from './place/place.component';
import {MatTabsModule} from '@angular/material/tabs';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatSelectModule} from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';
import {AddEditHomeStayComponent} from './home-stay/add-edit-home-stay/add-edit-home-stay.component';
import {AddEditPlaceComponent} from './place/add-edit-place/add-edit-place.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatCardModule} from '@angular/material/card';
import {AddEditCityComponent} from './address/add-edit-city/add-edit-city.component';
import {AddEditDistrictComponent} from './address/add-edit-district/add-edit-district.component';
import {AddEditVillageComponent} from './address/add-edit-village/add-edit-village.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {CKEditorModule} from 'ng2-ckeditor';
import {ComponentModule} from "../../shared/component/component.module";
import {NgImageSliderModule} from "ng-image-slider";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatChipsModule} from "@angular/material/chips";

@NgModule({
  declarations: [
    ManageHomeStayComponent,
    HomestayComponent,
    AddressComponent,
    DetailHomestayComponent,
    PlaceComponent,
    AddEditPlaceComponent,
    AddEditHomeStayComponent,
    AddEditCityComponent,
    AddEditDistrictComponent,
    AddEditVillageComponent,
  ],
    imports: [
        FormsModule,
        ReactiveFormsModule,
        CommonModule,
        ManageHomeStayRoutingModule,
        NbTreeGridModule,
        NbIconModule,
        Ng2SmartTableModule,
        NbSpinnerModule,
        NbCardModule,
        NbCheckboxModule,
        NbTabsetModule,
        NbPopoverModule,
        NbButtonModule,
        NbInputModule,
        NbSelectModule,
        NbTooltipModule,
        NbAccordionModule,
        NbAutocompleteModule,
        MatTabsModule,
        MatInputModule,
        MatFormFieldModule,
        MatIconModule,
        MatAutocompleteModule,
        MatSelectModule,
        MatButtonModule,
        MatDialogModule,
        MatCardModule,
        MatProgressSpinnerModule,
        CKEditorModule,
        ComponentModule,
        NgImageSliderModule,
        MatExpansionModule,
        MatChipsModule,
    ],

})
export class ManageHomeStayModule {
}
