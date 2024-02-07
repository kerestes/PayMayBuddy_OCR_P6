import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferComponent } from './transfer.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { BuddyService } from '../../services/buddyService/buddy.service';
import { RegisterService } from '../../services/registerService/register.service';
import { MatDialog } from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { assert } from 'node:console';
import { Transaction } from '../../models/transaction/transaction';
import { User } from '../../models/user/user';

describe('TransferComponent', () => {
  let component: TransferComponent;
  let fixture: ComponentFixture<TransferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers:[BuddyService, RegisterService, MatDialog],
      imports: [TransferComponent, HttpClientTestingModule, RouterTestingModule, BrowserAnimationsModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call getTransferList', ()=> {
    spyOn(component, 'getTransferList');
    component.ngOnInit()
    expect(component.getTransferList).toHaveBeenCalled();
  })

  it('should call getBuddyList', ()=> {
    spyOn(component, 'getBuddyList');
    component.ngOnInit()
    expect(component.getBuddyList).toHaveBeenCalled();
  })

  it('should render transfer table', () => {
    component.dataSource = transferList;
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;

    let verify:boolean = false;
    const els = compiled.querySelectorAll('td');
    els.forEach(element => {
      if(element.innerHTML.trim() == 'Lasceaux')
        verify = true;
    })

    expect(verify).toBeTrue();
  })

  it('should render buddyList table', async () => {

    const trigger = fixture.debugElement.nativeElement.querySelector('.mat-mdc-select');
    trigger.click();
    fixture.detectChanges();

    await fixture.whenStable().then(() => {
        expect(trigger).toBeTruthy();
    });
  })
});

export const transferList:Transaction[] = [
  {
    portefeuilleOrigine: {
      user: {
        id: 1,
        prenom: "Robert",
        nom: "Dupont",
        email: "robert@gmail.com"
      },
      solde: 0,
      updateDate: new Date()
    },
    portefeuilleDestination: {
      user: {
        id: 2,
        prenom: "Antoine",
        nom: "Lasceaux",
        email: "antoine@gmail.com"
      },
      solde: 1250,
      updateDate: new Date()
    },
    montantTotal: 0,
    montantLiquide: 190,
    taxe: 10,
    dateTransaction : new Date(2023, 11, 29)
  },
  {
    portefeuilleOrigine: {
      user: {
        id: 3,
        prenom: "Jean",
        nom: "Delfort",
        email: "jean@gmail.com"
      },
      solde: 2500,
      updateDate: new Date()
    },
    portefeuilleDestination: {
      user: {
        id: 1,
        prenom: "Robert",
        nom: "Dupont",
        email: "robert@gmail.com"
      },
      solde: 0,
      updateDate: new Date()
    },
    montantTotal: 100,
    montantLiquide: 95,
    taxe: 5,
    dateTransaction : new Date(2024, 1, 12)
  }
]

