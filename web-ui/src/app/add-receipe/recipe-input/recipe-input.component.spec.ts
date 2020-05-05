import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeInputComponent } from './recipe-input.component';

describe('RecipeInputComponent', () => {
  let component: RecipeInputComponent;
  let fixture: ComponentFixture<RecipeInputComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecipeInputComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecipeInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
